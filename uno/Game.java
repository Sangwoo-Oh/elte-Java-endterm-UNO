package uno;

import uno.util.NotEnoughPlayersException;
import uno.util.Card;
import uno.util.InputSource;
import uno.util.Color;
import uno.util.CardType;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.io.IOException;

public class Game {
    LinkedList<Card> deck;
    List<Player> players;
    Card currentCard;
    public List<Player> getPlayers() {return players;}
    int currentPlayerIdx;
    boolean isForward;
    boolean isOn;
    InputSource inputSource;

    public Game(int cardsPerPlayer, InputSource inputSource, String... players) throws NotEnoughPlayersException {
        if (players.length < 2) throw new NotEnoughPlayersException(players.length);
        this.inputSource = inputSource;
        initDeck();
        initPlayers(cardsPerPlayer, players);
        currentCard = drawCards(1).getFirst();
        currentPlayerIdx = 0;
        isForward = true;
        isOn = true;
    }

    private void initDeck() {
        deck = new LinkedList<Card>();
        for (Color color : Color.values()) {
            for (int i=1; i<=9; i++){
                deck.add(new Card(color, CardType.VALUE, i));
            }
            for (CardType type : CardType.values()){
                deck.add(new Card(color, type, 0));
                deck.add(new Card(color, type, 0));
            }
        }
        if (inputSource.isInteractive) {
            Collections.shuffle(deck);
        } else {
            Collections.shuffle(deck, new java.util.Random(12345));
        }
    }
    private void initPlayers(int cardsPerPlayer, String... players) {
        this.players = new ArrayList<Player>();
        for (int i=0;i<players.length;i++)
            this.players.add(new Player(players[i], drawCards(cardsPerPlayer), this));
    }

    private List<Card> drawCards(int numOfCards) {
        ArrayList<Card> dc = new ArrayList<Card>();
        for (int i=0; i<numOfCards;i++) {
            dc.add(deck.removeFirst());
        }
        return dc;
    }

    public static void main(String[] args) throws NotEnoughPlayersException, IOException{
        Game game = new Game(3, new InputSource(true), args);
        while (game.isOn) {
            game.playNext();
        }
    }
    public void playNext() throws IOException {
        int nextPlayerIdx = getNextPlayerIdx();
        interactiveMsg(players.get(nextPlayerIdx).toString());
        currentPlayerIdx = nextPlayerIdx;
        Player currentPlayer = players.get(currentPlayerIdx);

        int cardIdx = inputSource.getNextInput();
        if(!currentPlayer.getHand().get(cardIdx).isPlayableOver(currentCard)){
            currentPlayerDrawCard();
        } else {
            currentCard = currentPlayer.getHand().get(cardIdx);
            currentPlayer.removeFromHand(cardIdx);
            if (currentCard.type != CardType.VALUE) useCardEffect();
        }

        if(currentPlayer.getHand().size()==0) isOn = false;
    }
    public int getNextPlayerIdx(){
        if (isForward) {
            return (currentPlayerIdx + 1) % players.size();
        } else {
            return (currentPlayerIdx - 1) % players.size();
        }
    }
    private void currentPlayerDrawCard(){
        players.get(currentPlayerIdx).addToHand(drawCards(1));
    }
    public Player getCurrentPlayer(){
        return null;
    }
    private void interactiveMsg(String msg) {
        if (inputSource.isInteractive) System.out.println(msg);
    }
    private void useCardEffect() {
        switch(currentCard.type) {
            case CardType.REVERSE: isForward = isForward == true ? false : true; break;
            case CardType.SKIP: currentPlayerIdx = getNextPlayerIdx(); break;
            case CardType.TAKE: players.get(getNextPlayerIdx()).addToHand(drawCards(1)); break;
        }
    }
}

