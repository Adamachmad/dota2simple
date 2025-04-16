public class StrengthHero extends Hero {
    public StrengthHero(String name) {
        super(name, 700, 150, 30);
    }
    
    public static String[] getHeroOptions() {
        return new String[] {
            "Axe - Berserker's Call (Taunts enemies)",
            "Tidehunter - Ravage (Stuns all enemies)",
            "Dragon Knight - Dragon Tail (Stuns target)"
        };
    }
    
    @Override
    public int useSkill() {
        if (mana >= 50) {
            restoreMana(-50);
            System.out.println(name + " uses strength skill!");
            return damage + 30;
        }
        System.out.println("Not enough mana!");
        return 0;
    }
}