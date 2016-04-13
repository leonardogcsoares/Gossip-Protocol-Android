package soares.leonardo.com.gossipprotocol;

import android.view.View;

import java.util.HashMap;

/**
 * Created by leonardogcsoares on 4/12/2016.
 */
public interface Presenter {

    void layoutOnClickImpl(View v);

    void setPeerLayoutMessageReceived(int id);

    void setPeerColorToPending(Peer peer);

    void setPeerColorToReceived(Peer peer);

    void setPeersUnclickable();

    void setPeersClickable();

    void setPeerMessage(Peer peer, String peerMessage);

    void setAllPeersToPending(HashMap peerHash);
}
