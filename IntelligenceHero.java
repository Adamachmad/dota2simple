public class IntelligenceHero extends Hero {
    public IntelligenceHero(String name) {
        super(name, 400, 300, 25);
    }
    
    public static String[] getHeroOptions() {
        return new String[] {
            "Crystal Maiden - Frostbite (Freezes enemy)",
            "Lion - Finger of Death (Massive damage)",
            "Zeus - Thundergod's Wrath (Global damage)"
        };
    }
    
    @Override
    public int useSkill() {
        if (mana >= 60) {
            restoreMana(-60);
            System.out.println(name + " uses intelligence skill!");
            return damage * 3;
        }
        System.out.println("Not enough mana!");
        return 0;
    }
}