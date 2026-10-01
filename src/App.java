import java.util.Scanner;

public class App {
    static final int ANTAL_PLATSER = 20;
    static final double VUXENPRIS = 299.90;
    static final double BARNPRIS = 149.90;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long[] personnummer = new long[ANTAL_PLATSER];
        int val = 0;

        while (val != 8) {
            System.out.println();
            System.out.println("1. Lägg till en passagerare");
            System.out.println("2. Visa lediga platser");
            System.out.println("3. Beräkna vinsten");
            System.out.println("4. Hitta bokning");
            System.out.println("5. Ta bort bokning");
            System.out.println("6. Visa personer över och under 18 år");
            System.out.println("7. Boka fönsterplats");
            System.out.println("8. Avsluta");
            System.out.print("Välj: ");

            try {
                val = Integer.parseInt(scanner.nextLine());

                if (val == 1) {
                    laggTillPassagerare(personnummer, scanner);
                } else if (val == 2) {
                    visaLedigaPlatser(personnummer);
                } else if (val == 3) {
                    beraknaVinst(personnummer);
                } else if (val == 4) {
                    hittaBokning(personnummer, scanner);
                } else if (val == 5) {
                    taBortBokning(personnummer, scanner);
                } else if (val == 6) {
                    visaAldrar(personnummer);
                } else if (val == 7) {
                    bokaFonsterplats(personnummer, scanner);
                } else if (val == 8) {
                    System.out.println("Programmet avslutas.");
                } else {
                    System.out.println("Ogiltigt val.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Skriv en siffra mellan 1 och 8.");
            }
        }

        scanner.close();
    }

    public static void laggTillPassagerare(long[] personnummer, Scanner scanner) {
        int plats = hittaLedigPlats(personnummer);

        if (plats == -1) {
            System.out.println("Det finns inga lediga platser.");
            return;
        }

        System.out.print("Ange personnummer: ");

        try {
            String nummer = scanner.nextLine();

            if (nummer.length() < 8) {
                System.out.println("Personnumret måste innehålla minst 8 siffror.");
                return;
            }

            long personnummerTal = Long.parseLong(nummer);
            personnummer[plats] = personnummerTal;

            System.out.println("Passageraren är bokad på plats " + (plats + 1));
        } catch (NumberFormatException e) {
            System.out.println("Personnumret måste skrivas med siffror.");
        }
    }

    public static int hittaLedigPlats(long[] personnummer) {
        for (int i = 0; i < personnummer.length; i++) {
            if (personnummer[i] == 0) {
                return i;
            }
        }

        return -1;
    }

    public static void visaLedigaPlatser(long[] personnummer) {
        System.out.println();
        System.out.println("Lediga platser:");

        boolean finnsLedig = false;

        for (int i = 0; i < personnummer.length; i++) {
            if (personnummer[i] == 0) {
                System.out.print((i + 1) + " ");
                finnsLedig = true;
            }
        }

        if (!finnsLedig) {
            System.out.println("Det finns inga lediga platser.");
        } else {
            System.out.println();
        }
    }

    public static void beraknaVinst(long[] personnummer) {
        int antalBokade = 0;
        double vinst = 0;

        for (int i = 0; i < personnummer.length; i++) {
            if (personnummer[i] != 0) {
                antalBokade++;
                int alder = raknaAlder(personnummer[i]);

                if (alder < 18) {
                    vinst += BARNPRIS;
                } else {
                    vinst += VUXENPRIS;
                }
            }
        }

        System.out.println();
        System.out.println("Antal sålda biljetter: " + antalBokade);
        System.out.printf("Vinst: %.2f kr%n", vinst);
    }

    public static void hittaBokning(long[] personnummer, Scanner scanner) {
        System.out.print("Ange personnummer: ");

        try {
            long soktPersonnummer = Long.parseLong(scanner.nextLine());

            for (int i = 0; i < personnummer.length; i++) {
                if (personnummer[i] == soktPersonnummer) {
                    System.out.println("Bokningen finns på plats " + (i + 1));
                    return;
                }
            }

            System.out.println("Personnumret finns inte.");
        } catch (NumberFormatException e) {
            System.out.println("Personnumret måste skrivas med siffror.");
        }
    }

    public static void taBortBokning(long[] personnummer, Scanner scanner) {
        System.out.print("Ange personnummer: ");

        try {
            long soktPersonnummer = Long.parseLong(scanner.nextLine());

            for (int i = 0; i < personnummer.length; i++) {
                if (personnummer[i] == soktPersonnummer) {
                    personnummer[i] = 0;
                    System.out.println("Bokningen är borttagen.");
                    return;
                }
            }

            System.out.println("Personnumret finns inte.");
        } catch (NumberFormatException e) {
            System.out.println("Personnumret måste skrivas med siffror.");
        }
    }

    public static void visaAldrar(long[] personnummer) {
        System.out.println();
        System.out.println("Personer under 18 år:");

        boolean hittadeUnder = false;

        for (int i = 0; i < personnummer.length; i++) {
            if (personnummer[i] != 0 && raknaAlder(personnummer[i]) < 18) {
                System.out.println(personnummer[i] + " - plats " + (i + 1));
                hittadeUnder = true;
            }
        }

        if (!hittadeUnder) {
            System.out.println("Inga personer under 18 år.");
        }

        System.out.println();
        System.out.println("Personer över 18 år:");

        boolean hittadeOver = false;

        for (int i = 0; i < personnummer.length; i++) {
            if (personnummer[i] != 0 && raknaAlder(personnummer[i]) >= 18) {
                System.out.println(personnummer[i] + " - plats " + (i + 1));
                hittadeOver = true;
            }
        }

        if (!hittadeOver) {
            System.out.println("Inga personer över 18 år.");
        }
    }

    public static void bokaFonsterplats(long[] personnummer, Scanner scanner) {
        System.out.print("Ange personnummer: ");

        long nummer;

        try {
            String input = scanner.nextLine();

            if (input.length() < 8) {
                System.out.println("Personnumret måste innehålla minst 8 siffror.");
                return;
            }

            nummer = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Personnumret måste skrivas med siffror.");
            return;
        }

        System.out.println("Lediga fönsterplatser:");

        boolean finnsFonsterplats = false;

        for (int i = 0; i < personnummer.length; i++) {
            int platsnummer = i + 1;

            if (arFonsterplats(platsnummer) && personnummer[i] == 0) {
                System.out.print(platsnummer + " ");
                finnsFonsterplats = true;
            }
        }

        System.out.println();

        if (!finnsFonsterplats) {
            System.out.println("Det finns inga lediga fönsterplatser.");
            return;
        }

        System.out.print("Välj fönsterplats: ");

        int plats;

        try {
            plats = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Skriv ett platsnummer.");
            return;
        }

        if (plats < 1 || plats > ANTAL_PLATSER) {
            System.out.println("Ogiltig plats.");
            return;
        }

        if (!arFonsterplats(plats)) {
            System.out.println("Det är inte en fönsterplats.");
            return;
        }

        if (personnummer[plats - 1] != 0) {
            System.out.println("Platsen är redan bokad.");
            return;
        }

        personnummer[plats - 1] = nummer;
        System.out.println("Fönsterplats " + plats + " är bokad.");
    }

    public static boolean arFonsterplats(int plats) {
        return plats % 4 == 1 || plats % 4 == 0;
    }

    public static int raknaAlder(long personnummer) {
        String nummer = String.valueOf(personnummer);

        if (nummer.length() < 8) {
            return 0;
        }

        int ar = Integer.parseInt(nummer.substring(0, 4));
        int manad = Integer.parseInt(nummer.substring(4, 6));
        int dag = Integer.parseInt(nummer.substring(6, 8));

        java.time.LocalDate fodelsedatum =
                java.time.LocalDate.of(ar, manad, dag);

        java.time.LocalDate idag =
                java.time.LocalDate.now();

        int alder = idag.getYear() - fodelsedatum.getYear();

        if (idag.getMonthValue() < fodelsedatum.getMonthValue()
                || (idag.getMonthValue() == fodelsedatum.getMonthValue()
                && idag.getDayOfMonth() < fodelsedatum.getDayOfMonth())) {
            alder--;
        }

        return alder;
    }
}