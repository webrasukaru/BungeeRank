package net.devras.bungee.rank;

public enum Rank {
	NONE(0, ""),
	VIP(1, "§aVIP"),
	VIP_Plus(2, "§aVIP§e+"),
	VIP_PlusPlus(3, "§a§lVIP§1+§c+"),
	MVP(4, "§bMVP"),
	MVP_Plus(5, "§bMVP§e+"),
	MVP_PlusPlus(6, "§b§lMVP§e+§6+"),
	YY(7, "§c§lYT"),
	CREATOR(8, "§eCreator"),
	OP(9, "§e§lOP"),
	ADMIN(10, "§b§lADMIN");

	private int id;
	private String prefix;
	private String suffix;

	Rank(int id, String prefix) {
		this.id = id;
		this.prefix = prefix;
		this.suffix = "";
	}

	public static Rank getRank(int i) {
		Rank rank = Rank.NONE;
		for (Rank r : values()) {
			if (r.id == i) {
				return r;
			}
		}
		return rank;
	}

	public int getId() {
		return this.id;
	}
	public String getPrefix() {
		return this.prefix;
	}
	public String getSuffix() {
		return this.suffix;
	}

<<<<<<< HEAD
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

=======
>>>>>>> bungeerank/master
}
