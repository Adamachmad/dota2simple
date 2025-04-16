import java.util.Random;
import java.util.Scanner;

public class Game {
    private Hero player;
    private Enemy enemy;
    private Tower[] towers;
    private Scanner input = new Scanner(System.in);
    private Random rand = new Random();
    private int currentTowerIndex = 0;
    private boolean inLane = true;

    public static void main(String[] args) {
        new Game().start();
    }

    public void start() {
        setupGame();
        gameLoop();
    }

    private void setupGame() {
        System.out.println("=== DOTA 2 SIMPLIFIED ===");
        System.out.println("Destroy all towers to win!");
        createHero();
        createTowers();
        spawnEnemy();
    }

    private void createHero() {
        System.out.println("\nChoose your hero type:");
        System.out.println("1. Agility (High damage, mobile)");
        System.out.println("2. Strength (Durable, tanky)");
        System.out.println("3. Intelligence (Spellcasters, high mana)");
        
        int choice = input.nextInt();
        input.nextLine();
        
        String[] options = {};
        switch(choice) {
            case 1:
                options = AgilityHero.getHeroOptions();
                System.out.println("\nAgility Heroes:");
                break;
            case 2:
                options = StrengthHero.getHeroOptions();
                System.out.println("\nStrength Heroes:");
                break;
            case 3:
                options = IntelligenceHero.getHeroOptions();
                System.out.println("\nIntelligence Heroes:");
                break;
            default:
                System.out.println("Invalid choice, defaulting to Agility");
                options = AgilityHero.getHeroOptions();
        }
        
        for (int i = 0; i < options.length; i++) {
            System.out.println((i+1) + ". " + options[i]);
        }
        
        System.out.print("Select your hero: ");
        int heroChoice = input.nextInt();
        input.nextLine();
        
        String name = options[heroChoice-1].split(" - ")[0];
        
        switch(choice) {
            case 1: player = new AgilityHero(name); break;
            case 2: player = new StrengthHero(name); break;
            case 3: player = new IntelligenceHero(name); break;
            default: player = new AgilityHero(name);
        }
        
        System.out.println("\nYou have chosen: " + name + "!");
    }

    private void createTowers() {
        towers = new Tower[] {
            new Tower("Tier 1", 800, 25),
            new Tower("Tier 2", 1200, 35), 
            new Tower("Tier 3", 1600, 45)
        };
    }

    private void spawnEnemy() {
        String[] names = {"Slark", "Axe", "Lina", "Pudge", "Zeus"};
        enemy = new Enemy(names[rand.nextInt(names.length)], 
                        200 + rand.nextInt(200), 
                        15 + rand.nextInt(15));
        System.out.println("\nA wild " + enemy.getName() + " appears!");
    }

    private void gameLoop() {
        while(player.isAlive() && currentTowerIndex < towers.length) {
            showStatus();
            
            if (inLane) {
                System.out.println("\n1. Attack Enemy");
                System.out.println("2. Use Skill");
                System.out.println("3. Heal");
                System.out.println("4. Push Tower");
            } else {
                System.out.println("\n1. Attack Tower");
                System.out.println("2. Use Skill on Tower");
                System.out.println("3. Heal");
                System.out.println("4. Return to Lane");
                System.out.println("Tower HP: " + towers[currentTowerIndex].getHp() + "/" + 
                                 towers[currentTowerIndex].getMaxHp());
            }
            
            System.out.print("Choose action: ");
            int choice = input.nextInt();
            
            switch(choice) {
                case 1: 
                    if (inLane) {
                        attackEnemy();
                    } else {
                        attackTower();
                    }
                    break;
                    
                case 2: 
                    if (inLane) {
                        if (enemy.isAlive()) {
                            useSkillOnEnemy();
                        } else {
                            System.out.println("No enemy to use skill on!");
                        }
                    } else {
                        useSkillOnTower();
                    }
                    break;
                    
                case 3: 
                    player.heal(50);
                    System.out.println("You healed 50 HP!");
                    break;
                    
                case 4:
                    if (inLane) {
                        // Move to attack tower
                        inLane = false;
                        enemy = null;
                        System.out.println("\n=== MOVING TO ATTACK TOWER ===");
                        System.out.println("You are now facing the " + towers[currentTowerIndex].getName() + "!");
                    } else {
                        // Return to lane
                        inLane = true;
                        spawnEnemy();
                        System.out.println("\n=== RETURNING TO LANE ===");
                        System.out.println("Enemies have respawned!");
                    }
                    break;
                    
                default:
                    System.out.println("Invalid choice!");
            }
            
            // Counterattacks
            if (inLane && enemy != null && enemy.isAlive()) {
                enemy.attack(player);
            } else if (!inLane) {
                towers[currentTowerIndex].attack(player);
            }
            
            // Check tower destruction
            if (!inLane && towers[currentTowerIndex].isDestroyed()) {
                System.out.println("\n=== TOWER DESTROYED! ===");
                player.gainXP(200);
                currentTowerIndex++;
                inLane = true;
                if (currentTowerIndex < towers.length) {
                    System.out.println("The " + towers[currentTowerIndex].getName() + " looms in the distance!");
                }
                spawnEnemy();
            }
        }
        
        if (player.isAlive()) {
            System.out.println("\n=== VICTORY! ===");
            System.out.println("You destroyed all towers!");
        } else {
            System.out.println("\n=== DEFEAT ===");
            System.out.println("Your hero has fallen...");
        }
    }

    private void showStatus() {
        System.out.println("\n=== " + player.getName() + " ===");
        System.out.println("Location: " + (inLane ? "Lane" : "Attacking " + towers[currentTowerIndex].getName()));
        System.out.println("Level: " + player.getLevel());
        System.out.printf("HP: %d/%d | Mana: %d/%d | Damage: %d\n",
            player.getHp(), player.getMaxHp(),
            player.getMana(), player.getMaxMana(),
            player.getDamage());
        System.out.println("XP: " + player.getXP() + "/" + player.getXPToNextLevel());
            
        if (inLane && enemy != null && enemy.isAlive()) {
            System.out.println("\nEnemy: " + enemy.getName());
            System.out.println("Enemy HP: " + enemy.getHp() + "/" + enemy.getMaxHp());
        } else if (!inLane) {
            System.out.println("\nCurrent Target: " + towers[currentTowerIndex].getName());
        }
    }

    private void attackEnemy() {
        player.attack(enemy);
        if (!enemy.isAlive()) {
            player.gainXP(100);
            spawnEnemy();
        }
    }

    private void attackTower() {
        player.attack(towers[currentTowerIndex]);
        System.out.println("Tower HP: " + towers[currentTowerIndex].getHp() + "/" + 
                         towers[currentTowerIndex].getMaxHp());
    }

    private void useSkillOnEnemy() {
        int damage = player.useSkill();
        if (damage > 0) {
            enemy.takeDamage(damage);
            if (!enemy.isAlive()) {
                player.gainXP(100);
                spawnEnemy();
            }
        }
    }

    private void useSkillOnTower() {
        int damage = player.useSkill();
        if (damage > 0) {
            towers[currentTowerIndex].takeDamage(damage);
            System.out.println("Tower HP: " + towers[currentTowerIndex].getHp() + "/" + 
                             towers[currentTowerIndex].getMaxHp());
        }
    }

    private void clearCreepWave() {
        int xpGain = 80 + rand.nextInt(40);
        System.out.println("\n" + player.getName() + " clears a creep wave!");
        System.out.println("Gained " + xpGain + " XP!");
        player.gainXP(xpGain);
        
        if (!enemy.isAlive()) {
            spawnEnemy();
        }
    }
}