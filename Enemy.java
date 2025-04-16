public class Enemy {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int damage;
    
    public Enemy(String name, int hp, int damage) {
        this.name = name;
        this.maxHp = this.hp = hp;
        this.damage = damage;
    }
    
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public boolean isAlive() { return hp > 0; }
    
    public void takeDamage(int amount) { hp = Math.max(0, hp - amount); }
    
    public void attack(Hero target) {
        target.takeDamage(damage);
    }
}