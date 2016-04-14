# Gossip Protocol Android Implementation
My implementation of the Gossip protocol using 12 simulated peers on an Android device. Serves merely as a point of learning for using Bimodal Multicast in a broadcasting application.


### Main Differences from Real Bimodal Multicast Application
- Dissiminations simulates that the peer has a 1% chance to receive the new message. With a simple `random(100) == 1` condition.
- Does not consider that the Gossip protocol actually makes each peer select random peers and asks for a Digest of the messages it has, and receives those which it does not contain in its own Digest. Gossip in this app simply randomly selects a peer and checks if it has received the broadcasted message.
- Network latency is simply a simulated pause (100-600ms - max for Wi-Fi x Mobile Network), in both the Dissimination and Gossip thread, described below.

### How it works

The basic functioning of the app is that each app starts with a message `tst`, on clicking any of the 12 peers, that peer starts sending its baseMessage, "P1 for Peer 1, P2 for Peer2, etc..." to the other peers. 

Three threads contain the logic for executing the program:
 - A Dissimination thread, that's responsible for executing a continuous loop that checks if each of the peers have the message, if it doesn't, it sends the peer the broadcasted message with a 1% receival rate.
 - A Gossip thread, continously loops, executing the following logic: randomly chooses a peer, and verifies if it has the broadcasted message, if it doesn't it sends the peer the broadcasted message with a 1% receival rate.
 - A Timer that executes every 500ms (can be changed), and checks if all peers have received the message. If it has finishes the current broadcasted message and allows another to begin. Responsible for stopping both the Dissimination and Gossip thread.
The Gossip and Dissimination thread are initialized with the beggining of a new broadcast.

### Results
From a simple analysis of time for message to be fully broadcasted, we see that compared to a simple Dissimination, implementing Bimodal Multicast drastically reduces time for broadcasting of messages to reach all desired peers, as well as increases reliability for all peers to receive the message.

#To-Do
- [ ] Better structure of code for the app. More DRY.
- [ ] Define better `GossipProtocol.onClickPeer` function.
- [ ] Create `GossipProtocol.peerHasReceivedMessage()`. Substitute for ```java presenter.setPeerMessage(peer, baseMessage); peer.setHasNewMessage(true); presenter.setPeerColorToReceived(peer); ```
- [ ] comment code for better understanding.
- [ ] Create SettingsActivity for user to configure `networkLatency` in _ms_, `networkPacketLoss` in _1/%_, with Gossip or not _checkbox_.
