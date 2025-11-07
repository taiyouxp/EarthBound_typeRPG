public class Character {
    private int id;
    private String name;
    private int hp;

    public Character(int id, String name, int hp) {
        this.id = id;
        this.name = name;
        this.hp = hp;
    }

    public void takeDamage(int damagePoints) {
        this.hp -= damagePoints;
        System.out.println(name + " took " + damagePoints + " damage! HP now: " + this.hp);
        if(this.hp<0) {
            this.hp = 0;
            System.out.println("farewell...");
        } 
    }

    public void Heal (int healPoints) {
        this.hp += healPoints;
        System.out.println(name + " healed " + healPoints + " points! HP reached: " + this.hp +
        "\ncurrent limit is 100.");
        if(this.hp > 100) {
            this.hp = 100; // this is the maximum hp that 'hero' can hendle at the moment
        }
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public int getHp() {return hp;}
}
