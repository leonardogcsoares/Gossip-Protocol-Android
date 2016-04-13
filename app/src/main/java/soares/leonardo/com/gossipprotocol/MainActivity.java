package soares.leonardo.com.gossipprotocol;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Presenter {

    private static final String TAG = "MainActivity";

    private Controller controller;

    RelativeLayout peerLayout1;
    RelativeLayout peerLayout2;
    RelativeLayout peerLayout3;
    RelativeLayout peerLayout4;
    RelativeLayout peerLayout5;
    RelativeLayout peerLayout6;
    RelativeLayout peerLayout7;
    RelativeLayout peerLayout8;
    RelativeLayout peerLayout9;
    RelativeLayout peerLayout10;
    RelativeLayout peerLayout11;
    RelativeLayout peerLayout12;

    TextView peerMessage1;
    TextView peerMessage2;
    TextView peerMessage3;
    TextView peerMessage4;
    TextView peerMessage5;
    TextView peerMessage6;
    TextView peerMessage7;
    TextView peerMessage8;
    TextView peerMessage9;
    TextView peerMessage10;
    TextView peerMessage11;
    TextView peerMessage12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewsForActivity();

        controller = new GossipProtocol(this);

        controller.addPeerToHash(peerLayout1, peerMessage1, "P1");
        controller.addPeerToHash(peerLayout2, peerMessage2, "P2");
        controller.addPeerToHash(peerLayout3, peerMessage3, "P3");
        controller.addPeerToHash(peerLayout4, peerMessage4, "P4");
        controller.addPeerToHash(peerLayout5, peerMessage5, "P5");
        controller.addPeerToHash(peerLayout6, peerMessage6, "P6");
        controller.addPeerToHash(peerLayout7, peerMessage7, "P7");
        controller.addPeerToHash(peerLayout8, peerMessage8, "P8");
        controller.addPeerToHash(peerLayout9, peerMessage9, "P9");
        controller.addPeerToHash(peerLayout10, peerMessage10, "P10");
        controller.addPeerToHash(peerLayout11, peerMessage11, "P11");
        controller.addPeerToHash(peerLayout12, peerMessage12, "P12");

    }

    private void setViewsForActivity() {

        peerLayout1 = (RelativeLayout) findViewById(R.id.peer1);
        peerLayout2 = (RelativeLayout) findViewById(R.id.peer2);
        peerLayout3 = (RelativeLayout) findViewById(R.id.peer3);
        peerLayout4 = (RelativeLayout) findViewById(R.id.peer4);
        peerLayout5 = (RelativeLayout) findViewById(R.id.peer5);
        peerLayout6 = (RelativeLayout) findViewById(R.id.peer6);
        peerLayout7 = (RelativeLayout) findViewById(R.id.peer7);
        peerLayout8 = (RelativeLayout) findViewById(R.id.peer8);
        peerLayout9 = (RelativeLayout) findViewById(R.id.peer9);
        peerLayout10 = (RelativeLayout) findViewById(R.id.peer10);
        peerLayout11 = (RelativeLayout) findViewById(R.id.peer11);
        peerLayout12 = (RelativeLayout) findViewById(R.id.peer12);

        setPeersClickable();

        peerMessage1 = (TextView) findViewById(R.id.peerMessage1);
        peerMessage2 = (TextView) findViewById(R.id.peerMessage2);
        peerMessage3 = (TextView) findViewById(R.id.peerMessage3);
        peerMessage4 = (TextView) findViewById(R.id.peerMessage4);
        peerMessage5 = (TextView) findViewById(R.id.peerMessage5);
        peerMessage6 = (TextView) findViewById(R.id.peerMessage6);
        peerMessage7 = (TextView) findViewById(R.id.peerMessage7);
        peerMessage8 = (TextView) findViewById(R.id.peerMessage8);
        peerMessage9 = (TextView) findViewById(R.id.peerMessage9);
        peerMessage10 = (TextView) findViewById(R.id.peerMessage10);
        peerMessage11 = (TextView) findViewById(R.id.peerMessage11);
        peerMessage12 = (TextView) findViewById(R.id.peerMessage12);

    }

    public void layoutOnClickImpl(View v) {
        controller.onClickPeer(v);

    }

    public void setPeerLayoutMessageReceived(int id) {

    }

    @Override
    public void setPeerColorToPending(final Peer peer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                peer.getRelativeLayout().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPeerPending));
            }
        });

    }

    @Override
    public void setPeerColorToReceived(final Peer peer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                peer.getRelativeLayout().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPeerUpdated));
            }
        });

    }

    @Override
    public void setPeersUnclickable() {
        peerLayout1.setClickable(false);
        peerLayout2.setClickable(false);
        peerLayout3.setClickable(false);
        peerLayout4.setClickable(false);
        peerLayout5.setClickable(false);
        peerLayout6.setClickable(false);
        peerLayout7.setClickable(false);
        peerLayout8.setClickable(false);
        peerLayout9.setClickable(false);
        peerLayout10.setClickable(false);
        peerLayout11.setClickable(false);
        peerLayout12.setClickable(false);
    }

    @Override
    public void setPeersClickable() {
        peerLayout1.setClickable(true);
        peerLayout2.setClickable(true);
        peerLayout3.setClickable(true);
        peerLayout4.setClickable(true);
        peerLayout5.setClickable(true);
        peerLayout6.setClickable(true);
        peerLayout7.setClickable(true);
        peerLayout8.setClickable(true);
        peerLayout9.setClickable(true);
        peerLayout10.setClickable(true);
        peerLayout11.setClickable(true);
        peerLayout12.setClickable(true);
    }

    @Override
    public void setPeerMessage(final Peer peer, final String peerMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                peer.setPeerMessage(peerMessage);
            }
        });

    }

    @Override
    public void setAllPeersToPending(HashMap peerHash) {
        for (Object o : peerHash.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            setPeerColorToPending((Peer) pair.getValue());
            ((Peer) pair.getValue()).setHasNewMessage(false);
        }
    }
}
