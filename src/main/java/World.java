import org.flywaydb.core.Flyway;

public class World {
    // private static final int ACTIVE_SAVE_ID = 1;
    // private static final int INITIAL_STATE_TEMPLATE_ID = 101; // will implement the "save" slot system later 

    public static void main (String[] args){
        // configuring the flyway and testing
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        // checking if variables are all set 
        if(url == null || user == null || password == null) {
            System.out.println("FATAL ERROR: Database environments (DB_URL, DB_USER, DB_PASSWORD) are not set.");
            return; // Exit the application
        }

        try { 
            Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .baselineOnMigrate(true)
                .locations("classpath:db/migration") // "telling" Flyway where the scripts are
                .load();
            
            flyway.migrate(); // the news sql scripts will automatically migrate to the main database

            System.out.println("Flyway migration was successful.");
        
        } catch (Exception e) { 
            System.out.println("FATAL ERROR: Database migration failed.");
            e.printStackTrace();
            return; // exits if migration fails
        }

        // main block of code (just testing for now)
        CharacterDAO dao = new CharacterDAO();
        Character hero = dao.getCharacterById(1);
        
        hero.Heal(110); dao.updateHp(hero);
        hero.takeDamage(30);
        dao.updateHp(hero);

        hero.takeDamage(10);
        dao.updateHp(hero);
    }
}