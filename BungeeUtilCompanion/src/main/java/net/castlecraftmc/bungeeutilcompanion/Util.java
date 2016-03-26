package net.castlecraftmc.bungeeutilcompanion;

public class Util {
	public static String join(Iterable<?> elements, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (Object e : elements) {
			if (sb.length() > 0)
				sb.append(delimiter); 
			sb.append(e);
		}
		return sb.toString();
	}
}