package uno.util;

import java.util.Objects;

public class Card {
    public final Color color;
    public final CardType type;
    public final int value;

    public boolean equals(Object other) {
        if(other == this) return true;
        if(other == null) return false;
        if(!(other instanceof Card)) return false;
        Card otherCard = (Card)other;
        return color == otherCard.color && type == otherCard.type && value == otherCard.value;
    }
    public int hashCode() {
        return Objects.hash(color,type,value);
    }

    public Card(Color color , CardType type, int value) {
        this.color = color;
        this.type = type;
        if (type == CardType.VALUE) {
            this.value = value;
        } else {
            this.value = 0;
        }
    }

    public boolean isPlayableOver(Card card) {
        if (type != CardType.VALUE) {
            return card.type == CardType.VALUE && color == card.color;
        } else {
            return color == card.color || value == card.value;
        }
    }

    @Override
    public String toString() {
        return type == CardType.VALUE ? color + " " + value : color + " " + type;
    }
}
