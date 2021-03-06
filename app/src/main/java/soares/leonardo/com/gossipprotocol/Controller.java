package soares.leonardo.com.gossipprotocol;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by leonardogcsoares on 4/12/2016.
 */
public interface Controller {

    void addPeerToHash(RelativeLayout peerLayout, TextView peerMessage, String baseMessage);

    void printPeerHash();

    void onClickPeer(View view, boolean shouldGossip);

    boolean isMessageSentThroughNetwork();

}
