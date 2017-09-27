import java.sql.*;
import java.util.Scanner;
import java.util.logging.*;
//java -cp postgresql-9.4-1201.jdbc4.jar:. DBtest

public class test {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("jdbc:postgresql://localhost:5432/postgres <- IGNORE the query I used for reference" );
        System.out.println("Type URL");
        String url = scanner.next();
        System.out.println("Type User");
        String user = scanner.next();
        System.out.println("Type password");
        String password = scanner.next();
        Connection con = null;

        //CONNECTING
        System.out.println("connecting to database...");
        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(test.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);

        }
        System.out.println("connection established...");
        // Call main menu
        StartMenu(con);
    }
        // Function prints 'title','modelno', and 'currentstock' from component-table
    public static void HowManyComponents(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM component");
        while (rs.next()) {
            System.out.println("----------------------------------------------------------");
            System.out.println(rs.getString("modelno") + " " + rs.getString("title") + " " + rs.getString("currentstock"));
        }
        rs.close();
        st.close();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type anything to return to main menu");
        if (scanner.hasNext() == true) {
            StartMenu(con);
        }
    }
        // Main menu. Prints options and takes integer as choice
    public static void StartMenu(Connection con) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("============================");
        System.out.println("|       MENU SELECTION     |");
        System.out.println("============================");
        System.out.println("|        Options:          |");
        System.out.println("|        1. Show Stock     |");
        System.out.println("|        2. Comp-Systems   |");
        System.out.println("|        3. Price-List     |");
        System.out.println("|        4. Price-Offer    |");
        System.out.println("|        5. Execute Sale   |");
        System.out.println("|        6. Restocking list|");
        System.out.println("|        7. Exit           |");
        System.out.println("============================");
        System.out.println("What would you like to do?");

        int Response = Integer.parseInt(scanner.nextLine());
        if (Response == 1) try {
            HowManyComponents(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (Response == 2) try {
            ShowCompSys(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (Response == 3) {
            PriceList(con);
        }
        if (Response == 4) {
            PriceOfferMenu(con);
        }
        if (Response == 5) {
            MakeSale(con);
        }
        if (Response == 6) {
            Restocklist(con);
        }
        if (Response == 7) {
            Runtime.getRuntime().exit(0);
        }
    }
        // Prints 'title' from computersystem and calls 'howmany' for computing amount buildable
    public static void ShowCompSys(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Computersystems");

        while (rs.next()) {
            System.out.println("----------------------------------------------------------");
            System.out.println(rs.getString("title") + " " + HowMany(con, rs.getInt("cpu"), rs.getInt("ram"), rs.getInt("cases"), rs.getInt("gpu"), rs.getInt("mainboard")));
        }
        rs.close();
        st.close();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type anything to return to main menu");
        if (scanner.hasNext() == true) {
            StartMenu(con);
        }
    }
        // takes modelno of parts and returns smallest
    public static int HowMany(Connection con, int cpu, int ram, int Case, int gpu, int mainboard) throws SQLException {

        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        Statement st2 = con.createStatement();
        Statement st3 = con.createStatement();
        Statement st4 = con.createStatement();

        int NumberOfCPU = 0;
        int NumberOfRAM = 0;
        int NumberOfCASE = 0;
        int NumberOfMB = 0;
        int NumberOfGPU = 0;

        ResultSet cpuquery = st.executeQuery("SELECT currentstock FROM Component WHERE modelno =" + cpu);
        while (cpuquery.next()) {
            NumberOfCPU = cpuquery.getInt("currentstock");
        }
        ResultSet ramquery = st1.executeQuery("SELECT currentstock FROM Component WHERE modelno =" + ram);
        while (ramquery.next()) {
            NumberOfRAM = ramquery.getInt("currentstock");
        }
        ResultSet casequery = st2.executeQuery("SELECT currentstock FROM Component WHERE modelno =" + Case);
        while (casequery.next()) {
            NumberOfCASE = casequery.getInt("currentstock");
        }
        ResultSet gpuQuery = st3.executeQuery("SELECT currentstock FROM Component WHERE modelno =" + gpu);
        while (gpuQuery.next()) {
            NumberOfGPU = gpuQuery.getInt("currentstock");
        }
        ResultSet MbQuery = st4.executeQuery("SELECT currentstock FROM Component WHERE modelno =" + mainboard);
        while (MbQuery.next()) {
            NumberOfMB = MbQuery.getInt("currentstock");
        }


        int lowest;
        int[] numbers = {NumberOfCPU, NumberOfRAM, NumberOfCASE, NumberOfMB, NumberOfGPU};
        lowest = numbers[0];
        for (int index = 1; index < numbers.length; index++)
            if (numbers[index] < lowest) {
                lowest = numbers[index];
            }
        return lowest;
    }
    // Prints 'title' and price of every product, grouped by 'kind'
    private static void PriceList(Connection con) {
        try {
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            ResultSet PriceListSys = st2.executeQuery("SELECT * FROM computersystems");
            ResultSet PriceListParts = st.executeQuery("SELECT * FROM component ORDER BY kind");
            while (PriceListParts.next()) {
                //  System.out.printf(rs.getString("title")+rs.getDouble(""));
                System.out.println(PriceListParts.getString("kind") + PriceListParts.getString("title") + FinalPrice(PriceListParts.getDouble("price")) + "kr.");
            }
            while (PriceListSys.next()) {
                if (HowMany(con, PriceListSys.getInt("cpu"), PriceListSys.getInt("ram"), PriceListSys.getInt("cases"), PriceListSys.getInt("gpu"), PriceListSys.getInt("mainboard")) > 0) {
                    System.out.print(PriceListSys.getString("title") + System.lineSeparator()
                            + FindName(con, PriceListSys.getInt("mainboard"))
                            + FindPrice(con, PriceListSys.getInt("mainboard")) + "kr." + System.lineSeparator()
                            + FindName(con, PriceListSys.getInt("gpu"))
                            + FindPrice(con, PriceListSys.getInt("gpu")) + "kr." + System.lineSeparator()
                            + FindName(con, PriceListSys.getInt("ram"))
                            + FindPrice(con, PriceListSys.getInt("ram")) + "kr." + System.lineSeparator()
                            + FindName(con, PriceListSys.getInt("cpu"))
                            + FindPrice(con, PriceListSys.getInt("cpu")) + "kr." + System.lineSeparator()
                            + FindName(con, PriceListSys.getInt("cases"))
                            + FindPrice(con, PriceListSys.getInt("cases")) + "kr." + System.lineSeparator()
                    );
                    System.out.println((char) 27 + "[36m -------------------------------------------------------------" + (char) 27 + "[0m");
                }
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type anything to return to main menu");
            if (scanner.hasNext() == true) {
                StartMenu(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // takes modelno and returns value of 'price'
    public static double FindPrice(Connection con, int modelno) throws SQLException {
        double price = 0;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT price FROM component WHERE modelno =" + modelno);
        while (rs.next()) {
            price = rs.getDouble("price");
        }
        return FinalPrice(price);
    }
    // takes modelno and returns strings in 'title' and 'kind'
    public static String FindName(Connection con, int modelno) throws SQLException {
        String name = null;
        String kind = null;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM component WHERE modelno =" + modelno);
        while (rs.next()) {
            name = rs.getString("title");
            kind = rs.getString("kind");
        }
        return kind + name;
    }
    // menu for choosing system for offer
    public static void PriceOfferMenu(Connection con) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT title FROM computersystems");
        while (rs.next()) {
            System.out.println(rs.getString("title"));
        }
        System.out.println("Type in a system for an offer");
        String Response = "'" + scanner.next() + "'";
        System.out.println(PriceOffer(con, Response) + "kr.");
        for (int i = 2; i < 12; i++) {
            System.out.println(WithBulkdiscount(PriceOffer(con, Response), i) + "kr. per system by purchase of " + i);
        }
        System.out.println("Type anything to return to main menu");
        if (scanner.hasNext() == true) {
            StartMenu(con);
        }
    }
    // Takes name of system. Uses FindPrice and FinalPrice to return a total price.
    public static double PriceOffer(Connection con, String system) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM computersystems WHERE title =" + system);
        double p = 0;
        while (rs.next()) {
            p = (FinalPrice(FindPrice(con, rs.getInt("cpu")) + FindPrice(con, rs.getInt("mainboard")) + FindPrice(con, rs.getInt("ram")) + FindPrice(con, rs.getInt("gpu")) + FindPrice(con, rs.getInt("cases")))
            );
        }
        return p;
    }
    // casting and integer division to return a rounded up price ending in 99.99
    public static double FinalPrice(double price) {
        price = price * 1.3;
        int cast = (int) price;
        cast = (cast / 100) * 100;
        double PriceDone = (double) cast;
        PriceDone = PriceDone + 99.99;
        return PriceDone;
    }
    // Takes systemprice and number of systems, returns the price with applied discount of 2%
    public static double WithBulkdiscount(double Systemprice, int NRofSystems) {
        Systemprice = Systemprice * (1 - (0.00 + (0.02 * (NRofSystems - 1))));
        double roundOff = Math.round(Systemprice * 100.0) / 100.0;
        return roundOff;
    }
    // Menu for choosing part or system to buy
    public static void MakeSale(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT title, modelno FROM component");
        while (rs.next()) {
            System.out.println(rs.getString("title") + rs.getInt("modelno"));
        }
        Statement st1 = con.createStatement();
        ResultSet rs1 = st1.executeQuery("SELECT title FROM computersystems");
        while (rs1.next()) {
            System.out.println(rs1.getString("title"));
        }
        System.out.println("Type 'part' or 'system' followed by modelno or systemname. eg. 'part 1001'");
        Scanner scanner = new Scanner(System.in);
        String PartOrSystem = scanner.next();
        if (PartOrSystem.contentEquals("system")) {
            String Systemname = "'" + scanner.next() + "'";
            if (InStockSystem(con,Systemname)){
                FindParts(Systemname, con);}
        } else {
            RemovePart(Integer.parseInt(scanner.next()), con);
        }
        System.out.println("Type anything to return to main menu");
        if (scanner.hasNext() == true) {
            StartMenu(con);
        }
    }
    // Removes 1 of given part with UPDATE statement
    private static void RemovePart(int modelno, Connection con) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate("UPDATE component SET currentstock = currentstock-1 WHERE modelno =" + modelno);
        System.out.println("stock has been updated.");
    }
    // Takes systemname and calls RemovePart on all parts
    private static void FindParts(String Systemname, Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM computersystems WHERE title =" + Systemname);
        while (rs.next()) {
            RemovePart(rs.getInt("cpu"), con);
            RemovePart(rs.getInt("mainboard"), con);
            RemovePart(rs.getInt("ram"), con);
            RemovePart(rs.getInt("graphicscard"), con);
            RemovePart(rs.getInt("cases"), con);
        }
        System.out.println("stock has been updated.");
    }
    // Prints the difference between currentstock and preferredstock for all parts
    private static void Restocklist(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM component");
        System.out.println("Negative restock indicates surplus compared to preferred amount");
        while (rs.next()){
            System.out.println(rs.getString("title")+(rs.getInt("prefamtafterrestock")-rs.getInt("currentstock")));
        }
        System.out.println("Type anything to return to main menu");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext() == true) {
            StartMenu(con);
        }
    }
    // Boolean to check if a sale would reduce stock below minimuminventory
    private static boolean InStockPart (int modelno, Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM component WHERE modelno=" +modelno);
        while (rs.next()) {
            if ((rs.getInt("currentstock") <= rs.getInt("minimuminventory"))) {
                return false;
            }
        }
        return true;
    }
    // Same as above only on all parts in a given system
    private static boolean InStockSystem (Connection con,String Systemname) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM computersystems WHERE title =" + Systemname);
        while (rs.next()){
            System.out.println("Action would violate minimuminventory");
            if (InStockPart(rs.getInt("cpu"),con)==false) {return false;}
            if (InStockPart(rs.getInt("mainboard"),con)==false) {return false;}
            if (InStockPart(rs.getInt("ram"),con)==false) {return false;}
            if (InStockPart(rs.getInt("graphicscard"),con)==false) {return false;}
            if (InStockPart(rs.getInt("cases"),con)==false) {return false;}
        }
        return true;
    }
}