package hse.java.lectures.lecture3.tasks.atm;

import java.util.*;

public class Atm
{
    private static final Map<Integer, Denomination> IntToDenom = new HashMap<>();


    private final Map<Denomination, Integer> banknotes = new EnumMap<>(Denomination.class);

    public Atm()
    {
        for (Denomination d : Denomination.values())
        {
            banknotes.put(d, 0);
        }
    }

    public void deposit(Map<Integer, Integer> banknotes)
    {

        if (banknotes == null) throw new InvalidDepositException("Banknotes is null");
        for (var banknote : banknotes.entrySet())
        {
            int value = banknote.getKey();
            int count = banknote.getValue();

            if (count <= 0) throw new InvalidDepositException("Count must be positive. Your " +
                    "count: " + count);


            if (Denomination.getDenomFromValue(value) == null) throw new InvalidDepositException(
                    "Invalid nominal value: " + value);


        }

        for (var banknote : banknotes.entrySet())
        {
            Denomination d = Denomination.getDenomFromValue(banknote.getKey());

            this.banknotes.put(d, this.banknotes.get(d) + banknote.getValue());


        }
    }

    public Map<Integer, Integer> withdraw(int amount)
    {
        if (amount <= 0)
            throw new InvalidAmountException("Amount ust be positive. Your amount: " + amount);

        if (amount > getBalance()) throw new InsufficientFundsException("Not enough money in ATM");

        Map<Denomination, Integer> moneySet = new EnumMap<>(Denomination.class);

        int remaining = amount;

        List<Denomination> sortedDenoms = new ArrayList<>(List.of(Denomination.values()));
        sortedDenoms.sort(Comparator.comparingInt(Denomination::value).reversed());

        for (Denomination d : sortedDenoms)
        {
            int availableCnt = this.banknotes.get(d);

            if (availableCnt > 0 && remaining >= d.value)
            {
                int needed = remaining / d.value;

                int canTake = Math.min(needed, availableCnt);

                moneySet.put(d, canTake);
                remaining -= d.value * canTake;
            }
        }

        if (remaining != 0) throw new CannotDispenseException("Can't dispense suck amount of " +
                "money");

        Map<Integer, Integer> res = new HashMap<>();
        for (var banknote : moneySet.entrySet())
        {
            Denomination d = banknote.getKey();
            int cnt = banknote.getValue();

            this.banknotes.put(d, this.banknotes.get(d) - cnt);

            res.put(d.value, cnt);
        }
        return res;

    }

    public int getBalance()
    {
        int balance = 0;

        for (var banknote : banknotes.entrySet())
        {
            int cnt = banknote.getValue();
            int value = banknote.getKey().value;

            balance += cnt * value;
        }
        return balance;
    }

    private enum Denomination
    {
        D50(50),
        D100(100),
        D500(500),
        D1000(1000),
        D5000(5000);

        private static final HashMap<Integer, Denomination> intToDenom = new HashMap<>();

        static
        {
            for (Denomination d : values())
            {
                intToDenom.put(d.value(), d);
            }
        }

        private final int value;


        Denomination(int value)
        {
            this.value = value;
        }

        static Denomination getDenomFromValue(int value)
        {
            return intToDenom.get(value);
        }

        int value()
        {
            return value;
        }
    }

}
