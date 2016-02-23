package net.castlecraftmc.classabilities;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.PortalType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.permissions.events.PermissionEntityEvent;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;

import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;

public final class ClassAbilities extends JavaPlugin implements Listener,CommandExecutor {
    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public static Permission perms = null;
	private static Plugin plugin;
	public static EffectManager em;
    final static ArrayList<String> cooldown = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		em = new EffectManager(EffectLib.instance());
		plugin = this;
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new BeastMasterAbility(), this);
		getServer().getPluginManager().registerEvents(new ArcherAbility(), this);
		getServer().getPluginManager().registerEvents(new NecromancerAbility(), this);
		getServer().getPluginManager().registerEvents(new HerbalistAbility(), this);
		getServer().getPluginManager().registerEvents(new MageAbility(), this);
		getServer().getPluginManager().registerEvents(new BerserkerAbility(), this);
		getServer().getPluginManager().registerEvents(new MinerAbility(), this);
		getServer().getPluginManager().registerEvents(new ClericAbility(), this);
		getServer().getPluginManager().registerEvents(new WarriorAbility(), this);
		getServer().getPluginManager().registerEvents(new AssassinAbility(), this);
		getServer().getPluginManager().registerEvents(new GuardAbility(), this);
		getServer().getPluginManager().registerEvents(new MerchantAbility(), this);
		getConfig().options().copyDefaults(true);
		saveConfig();
        setupPermissions();
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
	}
	public void onDisable() {
		for (World w : Bukkit.getWorlds()) {
			String world = w.getName();
			for (LivingEntity e : Bukkit.getWorld(world).getLivingEntities()) {
				if (DisguiseAPI.isDisguised(e) == true && e instanceof Wolf && DisguiseAPI.getDisguise(e).getType() == DisguiseType.FALLING_BLOCK /* && DisguiseAPI.getDisguise(e) == ClassAbilitiesListener.goldPressurePlate*/) {
					e.remove();
				}
				if (DisguiseAPI.isDisguised(e) == true && e instanceof Wolf && DisguiseAPI.getDisguise(e).getType() == DisguiseType.SKELETON /* && DisguiseAPI.getDisguise(e) == ClassAbilitiesListener.goldPressurePlate*/) {
					e.remove();
				}
			}
		}
		em.disposeOnTermination();
		plugin = null;
	}
	@SuppressWarnings("unused")
	@EventHandler
	public void SignChange(SignChangeEvent e) {
		if (e.getLine(0).contentEquals("[Class]") && e.getLine(1).contentEquals("") == false && e.getLine(2).contentEquals("") == true && e.getLine(3).contentEquals("") == true && e.getPlayer().hasPermission("signcreate.*")) {
			Player p = e.getPlayer();
			int x = (int) e.getBlock().getLocation().getX();
			int y = (int) e.getBlock().getLocation().getY();
			int z = (int) e.getBlock().getLocation().getZ();
			String world = e.getBlock().getLocation().getWorld().getName();
			String xyz = Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(z);
			String toClassType = e.getLine(1).substring(0, e.getLine(1).length() - 1);
			String race = null;
			if (toClassType.contentEquals("Cleric") | toClassType.contentEquals("Warrior") | toClassType.contentEquals("Miner")) {
				race = "Dwarf";
			} else if (toClassType.contentEquals("Merchant") | toClassType.contentEquals("Guard") | toClassType.contentEquals("Assassin")) {
				race = "Human";
			} else if (toClassType.contentEquals("Mage") | toClassType.contentEquals("Archer") | toClassType.contentEquals("Herbalist")) {
				race = "Elf";
			} else if (toClassType.contentEquals("Beastmaster") | toClassType.contentEquals("Berserker") | toClassType.contentEquals("Necromancer")) {
				race = "Orc";
			}
			int toClassLevel = Character.getNumericValue(e.getLine(1).charAt(e.getLine(1).length() - 1));
			int price = 0;
			if (toClassLevel == 1) {
				price = 25000;
			} else if (toClassLevel == 2) {
				price = 250000;
			} else if (toClassLevel == 3) {
				price = 750000;
			}
			getConfig().set("Signs." + xyz + ".type", "Class");
			getConfig().set("Signs." + xyz + ".x", x);
			getConfig().set("Signs." + xyz + ".y", y);
			getConfig().set("Signs." + xyz + ".z", z);
			getConfig().set("Signs." + xyz + ".world", world);
			getConfig().set("Signs." + xyz + ".class", toClassType);
			getConfig().set("Signs." + xyz + ".level", toClassLevel);
			getConfig().set("Signs." + xyz + ".price", price);
			getConfig().set("Signs." + xyz + ".race", race);
			saveConfig();
			e.setLine(0, "");
			e.setLine(1, "§b" + toClassType + " " + toClassLevel);
			e.setLine(2, "§4$" + price);
			e.setLine(3, "");
		}
		if (e.getLine(0).contentEquals("[Race]") && e.getLine(1).contentEquals("") == false && e.getLine(2).contentEquals("") == true && e.getLine(3).contentEquals("") == true && e.getPlayer().hasPermission("signcreate.*")) {
			Player p = e.getPlayer();
			int x = (int) e.getBlock().getLocation().getX();
			int y = (int) e.getBlock().getLocation().getY();
			int z = (int) e.getBlock().getLocation().getZ();
			String world = e.getBlock().getLocation().getWorld().getName();
			String xyz = Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(z);
			String toRace = e.getLine(1);
			getConfig().set("Signs." + xyz + ".type", "Race");
			getConfig().set("Signs." + xyz + ".x", x);
			getConfig().set("Signs." + xyz + ".y", y);
			getConfig().set("Signs." + xyz + ".z", z);
			getConfig().set("Signs." + xyz + ".world", world);
			getConfig().set("Signs." + xyz + ".race", toRace);
			saveConfig();
			e.setLine(0, "");
			e.setLine(1, "§bClick to Become a");
			e.setLine(2, "§4" + toRace);
			e.setLine(3, "");
		}
	}
	@EventHandler
	public void SignRemove(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.SIGN_POST | e.getBlock().getType() == Material.WALL_SIGN) {
				for(String key : getConfig().getConfigurationSection("Signs").getKeys(false)){
					int x = getConfig().getInt("Signs."+key+".x");
					int y = getConfig().getInt("Signs."+key+".y");
					int z = getConfig().getInt("Signs."+key+".z");
					String world = getConfig().getString("Signs."+key+".world");
					if (e.getBlock().getX() == x && e.getBlock().getY() == y && e.getBlock().getZ() == z && e.getBlock().getWorld().getName().equals(world) && e.getPlayer().hasPermission("signbreak.*")) {
						getConfig().set("Signs." + key, null);
						saveConfig();
						e.getPlayer().sendMessage("§6[§aC§2C§6] §cYou removed a Class or Race sign!");
					}
					else if (e.getBlock().getX() == x && e.getBlock().getY() == y && e.getBlock().getZ() == z && e.getBlock().getWorld().getName().equals(world) && e.getPlayer().hasPermission("signbreak.*") == false) {
						e.setCancelled(true);
					}
				}
		}
	}
	@SuppressWarnings("unused")
	@EventHandler
	public void SignInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.SIGN_POST | e.getClickedBlock().getType() == Material.WALL_SIGN) {
			for(String key : getConfig().getConfigurationSection("Signs").getKeys(false)){
				Player p = (Player) e.getPlayer();
				int x = getConfig().getInt("Signs."+key+".x");
				int y = getConfig().getInt("Signs."+key+".y");
				int z = getConfig().getInt("Signs."+key+".z");
				String signType = getConfig().getString("Signs."+key+".type");
				String world = getConfig().getString("Signs."+key+".world");
				if (e.getClickedBlock().getX() == x && e.getClickedBlock().getY() == y && e.getClickedBlock().getZ() == z && e.getClickedBlock().getWorld().getName().equals(world)) {
					PermissionUser permsUser = PermissionsEx.getUser(p);
					if (signType.equals("Class")) {
						int level = getConfig().getInt("Signs."+key+".level");
						String type = getConfig().getString("Signs."+key+".class");
						String group = getConfig().getString("Signs."+key+".class") + getConfig().getString("Signs."+key+".level");
						Double price = getConfig().getDouble("Signs."+key+".price");
						String race = getConfig().getString("Signs."+key+".race");
						if (permsUser.inGroup(type + "1") && permsUser.inGroup(race)) {
							if (level == 1) {
								p.sendMessage("§6[§aC§2C§6] §cYou are already a(n) " + type + " level 1!");
							} else if (level == 2) {
								if (econ.has(p, price) == false) {
									p.sendMessage("§6[§aC§2C§6] §cYou need $" + (price - econ.getBalance(p)) + " more!");
								} else if (econ.has(p, price)) {
									permsUser.addGroup(type + "2");
									permsUser.removeGroup(type + "1");
									econ.withdrawPlayer(p, price);
									p.sendMessage("§6[§aC§2C§6] §cYou are now a(n) " + type + " level 2!");
								}
							} else if (level == 3) {
								p.sendMessage("§6[§aC§2C§6] §cYou must upgrade to " + type + " level 2 before upgrading to level 3!");
							}
						} else if (permsUser.inGroup(type + "2") && permsUser.inGroup(race)) {
							if (level == 1) {
								p.sendMessage("§6[§aC§2C§6] §cYou are already a higher level " + type + "!");
							} else if (level == 2) {
								p.sendMessage("§6[§aC§2C§6] §cYou are already a(n) " + type + " level 2!");
							} else if (level == 3) {
								if (econ.has(p, price) == false) {
								p.sendMessage("§6[§aC§2C§6] §cYou need $" + (price - econ.getBalance(p)) + " more!");
								} else if (econ.has(p, price)) {
									permsUser.addGroup(type + "3");
									permsUser.removeGroup(type + "2");
									econ.withdrawPlayer(p, price);
									p.sendMessage("§6[§aC§2C§6] §cYou are now a(n) " + type + " level 3!");
								}
							}
						} else if (permsUser.inGroup(type + "3") && permsUser.inGroup(race)) {
							if (level == 1) {
								p.sendMessage("§6[§aC§2C§6] §cYou are already a higher level " + type + "!");
							} else if (level == 2) {
								p.sendMessage("§6[§aC§2C§6] §cYou are already a higher level " + type + "!");
							} else if (level == 3) {
								p.sendMessage("§6[§aC§2C§6] §cYou are already a(n) " + type + " level 3!");
							}
						} else if (permsUser.inGroup(race)) {
							String[] classList = new String[]{"Merchant","Guard","Assassin","Beastmaster","Berserker","Necromancer","Cleric","Miner","Warrior","Mage","Archer","Herbalist"};	
							if (level == 1) {
								if (econ.has(p, price) == false) {
									p.sendMessage("§6[§aC§2C§6] §cYou need $" + (price - econ.getBalance(p)) + " more!");
								} else if (econ.has(p, price)) {
									for (String g : classList) {
										for (int i = 1; i < 4; i++) {
											if (permsUser.inGroup(g + String.valueOf(i))) {
												String classGroup = g + String.valueOf(i);
												permsUser.removeGroup(classGroup);
											}
										}
									}
									permsUser.addGroup(type + "1");
									econ.withdrawPlayer(p, price);
									p.sendMessage("§6[§aC§2C§6] §cYou are now a(n) " + type + " level 1!");
								}
							} else if (level == 2) {
								p.sendMessage("§6[§aC§2C§6] §cYou must upgrade to " + type + " level 1 before upgrading to level 2!");
							} else if (level == 3) {
								p.sendMessage("§6[§aC§2C§6] §cYou must upgrade to " + type + " level 2 before upgrading to level 3!");
							}
						} else if (permsUser.inGroup(race) == false) {
							p.sendMessage("§6[§aC§2C§6] §cYou need to be a(n) " + race + " to be a(n) " + type + "!");
							p.performCommand("spawn");
						}
					} else if (signType.equals("Race")) {
						if (isPlayerOnCooldown(p) == false) {
							String race = getConfig().getString("Signs."+key+".race");
							String uuid = p.getUniqueId().toString();
							if (permsUser.inGroup(race)) {
								p.sendMessage("§6[§aC§2C§6] §cYou are already a(n) " + race + "!");
							} else if (permsUser.inGroup(race) == false && permsUser.inGroup("Peasant") == false) {
								permsUser.removeGroup("Soulless");
								permsUser.addGroup("Peasant");
								permsUser.addGroup(race);
								getConfig().set("Players." + uuid + ".TimeLastChosen", System.currentTimeMillis());
								saveConfig();
								p.sendMessage("§6[§aC§2C§6] §cYou are now a(n) " + race + "!");
							} else if (permsUser.inGroup(race) == false && (permsUser.inGroup("Peasant")) == true) {
								String[] classList = new String[]{"Merchant","Guard","Assassin","Beastmaster","Berserker","Necromancer","Cleric","Miner","Warrior","Mage","Archer","Herbalist"};	
								String[] raceList = new String[]{"Human","Elf","Orc","Dwarf"};
								for (String g : classList) {
									for (int i = 1; i < 4; i++) {
										if (permsUser.inGroup(g + String.valueOf(i))) {
											String group = g + String.valueOf(i);
											permsUser.removeGroup(group);
										}
									}
								}
								for (String g : raceList) {
									if (permsUser.inGroup(g)) {
										permsUser.removeGroup(g);
										if (g.equalsIgnoreCase("human") && p.hasPotionEffect(PotionEffectType.JUMP)) {
											for (PotionEffect pe : p.getActivePotionEffects()) {
												if (pe.getType().equals(PotionEffectType.JUMP) && pe.getAmplifier() == 3) {
													p.removePotionEffect(PotionEffectType.JUMP);
												}
											}
										} else if (g.equalsIgnoreCase("elf") && p.hasPotionEffect(PotionEffectType.SPEED)) {
											for (PotionEffect pe : p.getActivePotionEffects()) {
												if (pe.getType().equals(PotionEffectType.SPEED) && pe.getAmplifier() == 2) {
													p.removePotionEffect(PotionEffectType.SPEED);
												}
											}
										}
									}
								}
								permsUser.removeGroup("Human");
								permsUser.removeGroup("Dwarf");
								permsUser.removeGroup("Orc");
								permsUser.removeGroup("Elf");
								permsUser.addGroup(race);
								getConfig().set("Players." + uuid + ".TimeLastChosen", System.currentTimeMillis());
								saveConfig();
								p.sendMessage("§6[§aC§2C§6] §cYou are now a(n) " + race + "!");
							}
						} else {
							p.sendMessage("§6[§aC§2C§6] §cYou must wait 3 days after choosing a race to get a different one!");
						}
					}
				}
			}
		}
	}
	public static Plugin getPlugin() {
		return plugin;
	}
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tr") && sender instanceof Player && sender.hasPermission("classabilities.requesttown") && cooldown.contains(sender.getName()) == false) {
			Resident r;
			try {
				r = TownyUniverse.getDataSource().getResident(sender.getName());
			} catch (NotRegisteredException e) {
				r = null;
			}
			if (Delay.isPlayerInTown(r) == true) {
				sender.sendMessage("§6[§aC§2C§6] §cYou are already a member of a town!");
				return true;
			} else if (Delay.isPlayerInTown(r) == false) {
				getServer().broadcastMessage(" ");
				getServer().broadcastMessage("§6[§aC§2C§6] §a " + sender.getName() + " is looking for a town!");
				getServer().broadcastMessage(" ");
				cooldown.add(sender.getName());
				Delay.canRequestTownCooldownDone((Player) sender, 6000);
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("tr") && sender instanceof Player && sender.hasPermission("classabilities.requesttown") && cooldown.contains(sender.getName()) == true) {
			sender.sendMessage("§6[§aC§2C§6] §cYou can only request a town every 5 minutes!");
			return true;
		}
		return false;
	}
    
    //Check if player is on cooldown
	public boolean isPlayerOnCooldown(Player p) {
		if (getConfig().getConfigurationSection("Players") != null) {
			for(String key : getConfig().getConfigurationSection("Players").getKeys(false)){
				Long timeLastChosen = getConfig().getLong("Players."+key+".TimeLastChosen");
				Long cooldownTime = getConfig().getLong("Configuration.CooldownTime");
				if (key.equals(p.getUniqueId().toString())) {
					//if current time - time last chosen race is greater than the cooldown time from seconds to milliseconds
					if ((System.currentTimeMillis() - timeLastChosen) < (cooldownTime * 1000)) {
						return true;
					}
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	//Rubbish to stop Enderdragon from making portal
	@EventHandler
	public void noEnderdragonPortal(EntityCreatePortalEvent e) {
		/*for (Block b : e.getBlocks()) {
			if (b.getType() == Material.BEDROCK) {
				e.setCancelled(true);
				return;
			}
		}*/
		if (e.getPortalType() == PortalType.ENDER) {
			e.setCancelled(true);
		}
	}
    /*@EventHandler
    public void onJoinAddGroups(PlayerJoinEvent e) {
    	Player p = e.getPlayer();
		PermissionUser player = PermissionsEx.getUser(p);
		@SuppressWarnings("deprecation")
		PermissionGroup[] Groups = player.getGroups();
		for (PermissionGroup g : Groups) {
			getServer().dispatchCommand(getServer().getConsoleSender(), "xfaddrank " + p.getName() + " " + g.getIdentifier());
		}
    }*/
}
