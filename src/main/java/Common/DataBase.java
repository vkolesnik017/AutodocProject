package Common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private String ip = "//10.10.28.99";
    private String url = "jdbc:mysql:" + ip + "/autodoc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String username = "alexeym";
    private String password = "24201901";
    private String skin;

    public DataBase() {

    }

    public DataBase(String skin) {
        this.skin = skin;
    }

    private Connection coonectionDB(String nameDB) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to " + nameDB + " DB succesfull!");
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
        return conn;
    }

    private String getNameRouteBySkin(String skin) {
        String routeName = null;

        switch (skin) {
            case "ATD":
                routeName = "routes_atd";
                break;
            case "PKW":
                routeName = "routes_pkw";
                break;
            case "BVS":
                routeName = "routes_bvs";
                break;
            case "DIR":
                routeName = "routes_dir";
                break;
            case "ERZ":
                routeName = "routes_erz";
                break;
            case "EU":
                routeName = "routes_eu";
                break;
            case "EXPERT":
                routeName = "routes_expert";
                break;
            case "KAUF":
                routeName = "routes_kauf";
                break;
            case "PRF":
                routeName = "routes_prf";
                break;
            case "TKF":
                routeName = "routes_tkf";
                break;
            case "TLS":
                routeName = "routes_tls";
                break;
            case "TOP":
                routeName = "routes_top";
                break;
            case "TSP":
                routeName = "routes_tsp";
                break;
            case "XXL":
                routeName = "routes_xxl";
                break;
        }
        return routeName;
    }

    // Return list from route Main By Shops getRouteListForMain("AT,DE,CH")
    List<String> getRouteListForMain(String shop) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB(getNameRouteBySkin(skin));
        ArrayList<String> route = new ArrayList<>();
        String query = "SELECT " + shop + " FROM autodoc." + getNameRouteBySkin(skin) + " where id = 1";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int columns = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    route.add(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return route;
    }

    // Return list routes By Shops and route getRouteListByRouteName("AT,DE,CH", "lkw_main")
    List<String> getRouteListByRouteName(String shop, String routeName) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB(getNameRouteBySkin(skin));
        ArrayList<String> route = new ArrayList<>();
        String query = "SELECT " + shop + " FROM autodoc." + getNameRouteBySkin(skin) + " where route_name = '" + routeName + "'";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int columns = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    route.add(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return route;
    }

    // Return String route By Shop and route or subroute getRouteByRouteName("AT", "lkw_main")
    public String getRouteByRouteName(String shop, String routeName) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB(getNameRouteBySkin(skin));
        ArrayList<String> route = new ArrayList<>();
        String query = "SELECT " + shop + " FROM autodoc." + getNameRouteBySkin(skin) + " where route_name = '" + routeName + "'";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int columns = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    route.add(resultSet.getString(i));
                }
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return route.get(0);
    }

    // Return String route By Shop and route("prod", "DE", "lkw_main")
    public String getFullRouteByRouteName(String envFromTest, String shop, String routeName) throws SQLException {
        String result;
        String env = new SetUp(skin).getEnv(envFromTest);
        String mainRoute = getRouteByRouteName(shop, routeName);
        result = env + mainRoute;
        return result;
    }

    // Return String route By Shop and route getRouteByRouteName("prod", "AT", "lkw_main", "product1")
    public String getFullRouteByRouteAndSubroute(String envFromTest, String shop, String routeName, String subRoute) throws SQLException {
        String result;
        String env = new SetUp(skin).getEnv(envFromTest);
        String mainRoute = getRouteByRouteName(shop, routeName);
        String subroute = getRouteByRouteName(shop, subRoute);
        result = env + mainRoute + "/" + subroute;
        return result;
    }


    // Return String Currency By Shop getCurrency("AT")
    public String getCurrency(String shop) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB("currency");
        String curency = null;
        String query = "SELECT " + shop + " FROM autodoc.currency where id = 1";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                curency = resultSet.getString(1);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return curency;
    }

    // Return String KBA By Shop getKba("AT")
    public String getKba(String shop) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB("kba_atd");
        String kba = null;
        String query = "SELECT " + shop + " FROM autodoc.kba_ATD where id = 1";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                kba = resultSet.getString(1);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return kba;
    }

    public String getTranslate(String dbName, String shop, String value) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB(dbName);
        String translation = null;
        String query = "SELECT ".concat(shop) + " FROM autodoc.".concat(dbName) + " where value=" + "\"".concat(value) + "\"";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                translation = resultSet.getString(1);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return translation;
    }

    public String getPaymentsLocator(String dbName, String shop, String payments_name) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB(dbName);
        String payments = null;
        String query = "SELECT ".concat(shop) + " FROM autodoc.".concat(dbName) + " where payments_name=" + "\"".concat(payments_name) + "\"";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                payments = resultSet.getString(1);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return payments;
    }

    public static String parseUserIdFromBD(String userData) {
        return userData.split("#")[0].trim();
    }

    public static String parseUserMailFromBD(String userData) {
        return userData.split("#")[1].trim();
    }

    public String getUserIdForPaymentsMethod(String dbName, String shop, String paymentsMethod) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB(dbName);
        String userID = null;
        String query = "SELECT ".concat(shop) + " FROM autodoc.".concat(dbName) + " where payments_name=" + "\"".concat(paymentsMethod) + "\"";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                userID = resultSet.getString(1);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return userID;
    }

    public String getAWSTranslationCountries(String translate) throws SQLException {
        Statement statement = null;
        String dbName = "aws_delivery_countries_translate";
        Connection conn = coonectionDB(dbName);
        String query = "SELECT aws.EN FROM autodoc." + dbName + " as aws where aws.DE = '" + translate + "'";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                translate = resultSet.getString(1);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return translate;
    }

    public ArrayList<String> getNameRequisitesMethod(String dbName, String shop, String... requisites) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB(dbName);
        String nameRequisites = null;
        ArrayList<String> finalList = new ArrayList<>();
        for (String req : requisites) {
            String query = "SELECT ".concat(shop) + " FROM autodoc.".concat(dbName) + " where Name_requisites=" + "\"".concat(req) + "\"";
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    nameRequisites = resultSet.getString(1);
                }
                if (nameRequisites != null)
                    finalList.add(nameRequisites);
                statement.close();
            } catch (SQLException state) {
                state.printStackTrace();
            } finally {
                if (statement != null) statement.close();
            }
        }
        conn.close();
        return finalList;
    }

    //Get firm date of BD
    public String getFirmData(String dbName, String shop, String expectedFirmData) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB(dbName);
        String expectedData = null;
        String query = "SELECT ".concat(shop) + " FROM autodoc.".concat(dbName) + " where dataFirm=" + "\"".concat(expectedFirmData) + "\"";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                expectedData = resultSet.getString(1);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return expectedData;
    }


    public String getMail(String value) throws SQLException {
        Statement statement = null;
        Connection conn = coonectionDB("mail_atd");
        String mail = null;
        String query = "SELECT ".concat("mail") + " FROM autodoc.".concat("mail_atd") + " where value=" + "\"".concat(value) + "\"";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                mail = resultSet.getString(1);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        }
        return mail;
    }
}
