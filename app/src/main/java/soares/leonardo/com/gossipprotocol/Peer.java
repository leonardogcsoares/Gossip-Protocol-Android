package soares.leonardo.com.gossipprotocol;

import android.graphics.Color;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by leonardogcsoares on 4/12/2016.
 */
public class Peer {

    private static final String TAG = "Peer";
    private RelativeLayout relativeLayout;
    private TextView peerMessage;
    private boolean hasNewMessage = false;

    public Peer(RelativeLayout relativeLayout, TextView peerMessage) {
        this.relativeLayout = relativeLayout;
        this.peerMessage = peerMessage;
    }

    public RelativeLayout getRelativeLayout() {
        return this.relativeLayout;
    }

    public int getId() {
        return this.relativeLayout.getId();
    }

    public void setPeerMessage(String peerMessage) {
        this.peerMessage.setText(peerMessage);
    }

    public String getPeerMessage() {
        return this.peerMessage.getText().toString();
    }

    public boolean hasNewMessage() {
        return hasNewMessage;
    }

    public void setHasNewMessage(boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    @Override
    public String toString() {
        return "Peer Id: " + this.relativeLayout.toString();
    }
}
