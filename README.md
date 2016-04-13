# Gossip Protocol Android Implementation
My implementation of the Gossip protocol using 12 simulated peers on an Android device. Serves merely as a point of understanding for using Bimodal Multicast in a broadcasting application.

Dissiminations simulates that the peer has a 1% chance to receive the new message, does not consider that the more peers that have the message, the more the new message will be broadcasted.

#To-Do
- [ ] Better structure of code for the app. More DRY.
- [ ] Define better `GossipProtocol.onClickPeer` function.
- [ ] Create `GossipProtocol.peerHasReceivedMessage()`. Substitute for ```java presenter.setPeerMessage(peer, baseMessage); peer.setHasNewMessage(true); presenter.setPeerColorToReceived(peer); ```
- [ ] comment code for better understanding.
