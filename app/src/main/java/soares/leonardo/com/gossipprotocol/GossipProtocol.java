package soares.leonardo.com.gossipprotocol;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leonardogcsoares on 4/12/2016.
 */
public class GossipProtocol implements Controller{

    private static final String TAG = "GossipProtocol";

    private static boolean isGossiping = false;
    private static String baseMessage = "";

    private final Presenter presenter;

    private HashMap peerHash = new HashMap();
    private final Object objectLock = new Object();
    Random random = new Random();

    private static long startTime;

    Thread dissiminationThread = null;
    Thread gossipThread = null;

    Timer timer;

    // Considering that 1% of packets sent are lost
    private double networkPacketLossPercent = 50;

    // Considering network latency of 600ms (Max for )
    private double networkLatency = 50; // In milliseconds


    public GossipProtocol(Activity activity) {
        this.presenter = (Presenter) activity;
        // This timer repeats every 5 seconds and checks if all peers have received the new message,
        // simulates the job of the tracker (be it central or distributed). Takes 2 seconds to start.

    }

    private boolean checkAllPeersHaveMessage() {

        Iterator it = peerHash.entrySet().iterator();
        Peer peer;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            peer = (Peer) pair.getValue();
            // If any one peer is not updated, continue messaging
            if (!peer.hasNewMessage())
                return false;
        }

        return true;
    }

    @Override
    public synchronized void addPeerToHash(RelativeLayout relativeLayout, TextView peerMessage, String baseMessage) {
        peerHash.put(relativeLayout.getId(), new Peer(relativeLayout, peerMessage, baseMessage));
    }

    @Override
    public void printPeerHash() {
        Log.d(TAG, peerHash.toString());
    }

    @Override
    public void onClickPeer(View view, boolean shouldGossip) {

        presenter.setAllPeersToPending(peerHash);

        Peer peer = (Peer) peerHash.get(view.getId());

        baseMessage = peer.baseMessage;
        peer.setPeerMessage(baseMessage);
        peer.setHasNewMessage(true);
        presenter.setPeerColorToReceived(peer);

        isGossiping = true;
        presenter.setPeersUnclickable();


        startTime = System.currentTimeMillis();

        if (dissiminationThread == null) {
            dissiminationThread = new Thread(new Thread(new DissiminationRunnable()));
            dissiminationThread.start();
        }
        if (gossipThread == null && shouldGossip) {
            gossipThread = new Thread(new Thread(new GossipRunnable()));
            gossipThread.start();
        }

        timer = new Timer("Timer");
        setStartFinishedGossipTimer();
    }

    @Override
    public boolean isMessageSentThroughNetwork() {
        return random.nextInt((int) networkPacketLossPercent) == 1;
    }

    private class DissiminationRunnable implements Runnable {

        HashMap updatedHash = new HashMap();
        HashMap pendingHash = new HashMap();

        @Override
        public void run() {
            Log.d(TAG, "Dissimination thread started");

            while (isGossiping) {
//                Log.d(TAG, "Dissimination thread running");
                Iterator it = peerHash.entrySet().iterator();
                Peer peer;
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    peer = (Peer) pair.getValue();
                    if (peer.hasNewMessage()) {
                        try {
                            // This simulates the latency of the network, in it that it takes X ms
                            // for the message to reach its destination peer.
                            Thread.sleep((long) networkLatency);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dissiminateMessage();
                    }
                }

            }
        }
    }

    private void dissiminateMessage() {
        Iterator it = peerHash.entrySet().iterator();
        Peer peer;

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            peer = (Peer) pair.getValue();
            if (isMessageSentThroughNetwork()) {
//                Log.d(TAG, "networkPacketLoss == 1");
                presenter.setPeerMessage(peer, baseMessage);
                peer.setHasNewMessage(true);
                presenter.setPeerColorToReceived(peer);
            }

        }

    }

    private class GossipRunnable implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "Gossip thread started");
            while(isGossiping) {

                // This creates a list with all the peers that do not yet have the message.
                List allPeers = new ArrayList();
                addAllPeersToList(allPeers);

                Iterator it = peerHash.entrySet().iterator();
                Peer peer;
                try {
                    // This simulates the latency of the network, in it that it takes X ms
                    // for the message to reach its destination peer.
                    Thread.sleep((long) networkLatency);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Iterates through each of the 12 peers in the HashMap
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    peer = (Peer) pair.getValue();

                    // If the current peer has the message, it asks a random peer if it has the message.
                    if (peer.hasNewMessage()) {
                        int position = random.nextInt(allPeers.size());
                        Peer randomPeer = (Peer) allPeers.get(position);

                        // Does the random peer have the message?
                        if (!randomPeer.hasNewMessage()) {
                            // Is the message lost in the network (1% success - default)
                            if (isMessageSentThroughNetwork()) {
                                Log.d(TAG, "Has gossiped");
                                presenter.setPeerMessage(randomPeer, baseMessage);
                                randomPeer.setHasNewMessage(true);
                                presenter.setPeerColorToReceived(randomPeer);
                            }

                        }
                    }

                }
            }
        }

        private void addAllPeersToList(List allPeers) {
            Iterator it = peerHash.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                allPeers.add((Peer) pair.getValue());
            }
        }
    }

    private void setStartFinishedGossipTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If all peers have message, gossiping is turned off.
                isGossiping = !checkAllPeersHaveMessage();
                if (!isGossiping) {
                    presenter.setPeersClickable();
                    Log.d(TAG, "Finished Gossiping");
                    isGossiping = false;
                    Log.d(TAG, "baseMesage: " + baseMessage);
                    presenter.showElapsedTime(System.currentTimeMillis() - startTime);

                    dissiminationThread = null;
                    gossipThread = null;
                    timer.cancel();
                    timer.purge();
                    timer = null;

                }
            }
        }, 2000, 500);
    }


}
