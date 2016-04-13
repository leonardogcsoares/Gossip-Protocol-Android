package soares.leonardo.com.gossipprotocol;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by leonardogcsoares on 4/12/2016.
 */
public interface Controller {

    public void addPeerToHash(RelativeLayout peerLayout, TextView peerMessage, String baseMessage);

    public void printPeerHash();

    public void onClickPeer(View view);

}
