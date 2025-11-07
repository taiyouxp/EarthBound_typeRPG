import java.sql.*;

public class CharacterDAO {
    private final String url = System.getenv("DB_URL");
    private final String user = System.getenv("DB_USER");
    private final String password = System.getenv(("DB_PASSWORD"));

    public Connection connect() throws SQLException {
        if(url == null || user == null || password == null) {
            throw new SQLException("Database environment variables(DB_URL, DB_USER, DB_PASSWORD) are not set.");
        } 
        return DriverManager.getConnection(url, user, password);
    }
    
    public Character getCharacterById(int id) {
        Character c = null;
        String SQL = "SELECT id, name, hp FROM characters WHERE id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                c = new Character(rs.getInt("id"), rs.getString("name"), rs.getInt("hp"));
            }
        } catch(SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return c;
    }

    public void updateHp(Character c) {
        String SQL = "UPDATE characters SET hp = ? WHERE id = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, c.getHp());
            pstmt.setInt(2, c.getId());
            pstmt.executeUpdate();
            System.out.println("Database updated: " + c.getName() + " HP = " + c.getHp());
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();    
        }
    }
}