package hse.java.lectures.lecture3.tasks.atm;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class Atm
{
    private final Map<Denomination, Integer> banknotes = new EnumMap<>(Denomination.class);

    public Atm()
    {
    }

    public void deposit(Map<Integer, Integer> banknotes) {}

    public Map<Integer, Integer> withdraw(int amount)
    {
        return Map.of();
    }

    public int getBalance()
    {
        return 0;
    }

    private enum Denomination
    {
        D50(50),
        D100(100),
        D500(500),
        D1000(1000),
        D5000(5000);

        private final int value;

        Denomination(int value)
        {
            this.value = value;
        }

        public static Denomination fromInt(int value)
        {
            return Arrays.stream(values()).filter(v -> v.value == value)
                    .findFirst()
                    .orElse(null);
        }

        int value()
        {
            return value;
        }
    }

}
