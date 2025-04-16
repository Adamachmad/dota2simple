public abstract class Hero {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int mana;
    protected int maxMana;
    protected int damage;
    protected int level;
    protected int xp;
    protected int xpToNextLevel;
    
    public Hero(String name, int hp, int mana, int damage) {
        this.name = name;
        this.maxHp = this.hp = hp;
        this.maxMana = this.mana = mana;
        this.damage = damage;
        this.level = 1;
        this.xp = 0;
        this.xpToNextLevel = 100;
    }
    
    // Getters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public int getDamage() { return damage; }
    public int getLevel() { return level; }
    public int getXP() { return xp; }
    public int getXPToNextLevel() { return xpToNextLevel; }
    public boolean isAlive() { return hp > 0; }
    
    // Combat
    public void takeDamage(int amount) { hp = Math.max(0, hp - amount); }
    public void heal(int amount) { hp = Math.min(maxHp, hp + amount); }
    public void restoreMana(int amount) { mana = Math.min(maxMana, mana + amount); }
    
    public void gainXP(int amount) {
        xp += amount;
        System.out.println(name + " gained " + amount + " XP!");
        if (xp >= xpToNextLevel) {
            levelUp();
        }
    }
    
    public void levelUp() {
        level++;
        maxHp += 50;
        maxMana += 20;
        damage += 5;
        hp = maxHp;
        mana = maxMana;
        xp -= xpToNextLevel;
        xpToNextLevel = (int)(xpToNextLevel * 1.5);
        System.out.println("\n=== LEVEL UP! (" + level + ") ===");
        System.out.println("| HP: +50 | Mana: +20 | Damage: +5 |");
    }
    
    public void attack(Enemy target) {
        System.out.println(name + " attacks for " + damage + " damage!");
        target.takeDamage(damage);
    }
    
    public void attack(Tower target) {
        System.out.println(name + " attacks tower for " + damage + " damage!");
        target.takeDamage(damage);
    }
    
    public abstract int useSkill();
}