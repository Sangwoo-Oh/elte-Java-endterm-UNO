package uno;

import java.util.List;
import uno.util.Card;
import uno.util.CardType;

public class Player {
    private String name;
    public String getName() {return name;}

    private List<Card> hand;
    public List<Card> getHand() {return hand;}

    private Game game;

    public Player(String name, List<Card> hand, Game game) {
        this.name = name;
        this.hand = hand;
        this.game = game;
    };

    public void addToHand(List<Card> cards){
        hand.addAll(cards);
    }

    public void removeFromHand(int cardIdx) {
        hand.remove(cardIdx);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player " + name + " ");
        int i = 1;
        for(Card card : hand) {
            sb.append(i + "=");
            if (card.isPlayableOver(game.currentCard)) {
                sb.append("*");
            }
            sb.append(card);
            if (i == hand.size()) sb.append("\n"); else sb.append(" ");
            i++;
        }

        return sb.toString();
    }

}
