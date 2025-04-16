public class AgilityHero extends Hero {
    public AgilityHero(String name) {
        super(name, 500, 200, 35);
    }
    
    public static String[] getHeroOptions() {
        return new String[] {
            "Juggernaut - Blade Fury (Spins dealing damage)",
            "Phantom Assassin - Stifling Dagger (Throws a dagger)",
            "Templar Assassin - Refraction (Blocks damage)"
        };
    }
    
    @Override
    public int useSkill() {
        if (mana >= 40) {
            restoreMana(-40);
            System.out.println(name + " uses agility skill!");
            return damage * 2;
        }
        System.out.println("Not enough mana!");
        return 0;
    }
}