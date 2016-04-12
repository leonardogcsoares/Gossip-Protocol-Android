package soares.leonardo.com.gossipprotocol;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leonardogcsoares on 4/12/2016.
 */
public class GossipProtocol implements Controller{

    private static final String TAG = "GossipProtocol";

    private static float networkPacketLoss = 0.99f;
    private static boolean isGossiping = false;
    private final Presenter presenter;

    private HashMap peerHash = new HashMap();
    private final Object objectLock = new Object();

    Thread dissiminationThread = new Thread(new DissiminationRunnable());
    Thread gossipThread = new Thread(new GossipRunnable());

    Timer timer = new Timer("Timer");


    public GossipProtocol(Activity activity) {
        this.presenter = (Presenter) activity;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If all peers have message, gossiping is turned off.
                isGossiping = !checkAllPeersHaveMessage();
                if (!isGossiping)
                    presenter.setPeersClickable();
            }
        }, 0, 5000);
    }

    private boolean checkAllPeersHaveMessage() {

        boolean allPeersUpdated = true;

        Iterator it = peerHash.entrySet().iterator();
        Peer peer;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            peer = (Peer) pair.getValue();
            // If any one peer is not updated, continue messaging
            allPeersUpdated = peer.hasNewMessage();
            if (!allPeersUpdated)
                break;
        }

        return allPeersUpdated;
    }

    @Override
    public void setNetworkPacketLoss(float networkPacketLoss) {
        GossipProtocol.networkPacketLoss = networkPacketLoss;
    }

    @Override
    public synchronized void addPeerToHash(RelativeLayout relativeLayout, TextView peerMessage) {
        peerHash.put(relativeLayout.getId(), new Peer(relativeLayout, peerMessage));
    }

    @Override
    public void printPeerHash() {
        Log.d(TAG, peerHash.toString());
    }

    @Override
    public void onClickPeer(View view) {

        Peer peer = (Peer) peerHash.get(view.getId());
        peer.setHasNewMessage(true);
        presenter.setPeerColorToPending(peer);
        isGossiping = true;

        presenter.setPeersUnclickable();

        dissiminationThread.start();
        gossipThread.start();
    }

    private class DissiminationRunnable implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "Dissimination thread started");

            while (isGossiping) {

            }
        }
    }

    private class GossipRunnable implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "Gossip thread started");

            while(isGossiping) {

            }
        }
    }


}
