package com.openthid.spacepenguins;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * @author Scott Ramsay A lazy font system
 */
public class FontService {
	public static final FontService ubuntuMedium = new FontService("ubuntu-font-family/Ubuntu-M.ttf", "ubuntuMedium");

	private String name;
	private FreeTypeFontGenerator generator;
	private ArrayList<Tuple<FontKey, BitmapFont>> cache;

	public FontService(String filename, String name) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal(filename));
		cache = new ArrayList<Tuple<FontKey, BitmapFont>>();
	}

	public BitmapFont getFont(int size) {
		return getFont(size, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()<>{}[]=?: ");
	}

	public BitmapFont getFont(int size, String characters) {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		parameter.characters = characters;
		return getFont(parameter);
	}

	public BitmapFont getFont(FreeTypeFontParameter parameter) {
		return getFont(new FontKey(parameter));
	}

	public BitmapFont getFont(FontKey key) {
		if (cache.stream().noneMatch(x -> x.a.equals(key))) {
			cache.add(new Tuple<FontService.FontKey, BitmapFont>(key, generator.generateFont(key.parameter)));
			Gdx.app.log("Font", "Generating new font: " + key);// + name + "
																// Size: " +
																// key.parameter.size);
		}
		return cache.stream().filter(x -> x.a.equals(key)).findAny().get().b;
	}

	private static class FontKey {

		FreeTypeFontParameter parameter;

		public FontKey(FreeTypeFontParameter parameter) {
			this.parameter = parameter;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((parameter == null) ? 0 : freeTypeFontParameterHashCode(parameter));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FontKey other = (FontKey) obj;
			if (parameter == null) {
				if (other.parameter != null)
					return false;
			} else if (!parameter.equals(other.parameter))
				return false;
			return true;
		}

		/**
		 * What Should be the hashCode() instance of FreeTypeFontParameter
		 */
		public static int freeTypeFontParameterHashCode(FreeTypeFontParameter parameter) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((parameter.borderColor == null) ? 0 : parameter.borderColor.hashCode());
			result = prime * result + parameter.borderGlyphCount;
			result = prime * result + (parameter.borderStraight ? 1231 : 1237);
			result = prime * result + Float.floatToIntBits(parameter.borderWidth);
			result = prime * result + ((parameter.characters == null) ? 0 : parameter.characters.hashCode());
			result = prime * result + ((parameter.color == null) ? 0 : parameter.color.hashCode());
			result = prime * result + (parameter.flip ? 1231 : 1237);
			result = prime * result + (parameter.genMipMaps ? 1231 : 1237);
			result = prime * result + (parameter.incremental ? 1231 : 1237);
			result = prime * result + (parameter.kerning ? 1231 : 1237);
			result = prime * result + ((parameter.magFilter == null) ? 0 : parameter.magFilter.hashCode());
			result = prime * result + ((parameter.minFilter == null) ? 0 : parameter.minFilter.hashCode());
			result = prime * result + ((parameter.packer == null) ? 0 : parameter.packer.hashCode());
			result = prime * result + ((parameter.shadowColor == null) ? 0 : parameter.shadowColor.hashCode());
			result = prime * result + parameter.shadowGlyphCount;
			result = prime * result + parameter.shadowOffsetX;
			result = prime * result + parameter.shadowOffsetY;
			result = prime * result + parameter.size;
			return result;
		}

		/**
		 * What Should be the equals() instance of FreeTypeFontParameter
		 */
		public static boolean freeTypeFontParameterEquals(Object obj, FreeTypeFontParameter parameter) {
			if (parameter == obj)
				return true;
			if (obj == null)
				return false;
			if (parameter.getClass() != obj.getClass())
				return false;
			FreeTypeFontParameter other = (FreeTypeFontParameter) obj;
			if (parameter.borderColor == null) {
				if (other.borderColor != null)
					return false;
			} else if (!parameter.borderColor.equals(other.borderColor))
				return false;
			if (parameter.borderGlyphCount != other.borderGlyphCount)
				return false;
			if (parameter.borderStraight != other.borderStraight)
				return false;
			if (Float.floatToIntBits(parameter.borderWidth) != Float.floatToIntBits(other.borderWidth))
				return false;
			if (parameter.characters == null) {
				if (other.characters != null)
					return false;
			} else if (!parameter.characters.equals(other.characters))
				return false;
			if (parameter.color == null) {
				if (other.color != null)
					return false;
			} else if (!parameter.color.equals(other.color))
				return false;
			if (parameter.flip != other.flip)
				return false;
			if (parameter.genMipMaps != other.genMipMaps)
				return false;
			if (parameter.incremental != other.incremental)
				return false;
			if (parameter.kerning != other.kerning)
				return false;
			if (parameter.magFilter != other.magFilter)
				return false;
			if (parameter.minFilter != other.minFilter)
				return false;
			if (parameter.packer == null) {
				if (other.packer != null)
					return false;
			} else if (!parameter.packer.equals(other.packer))
				return false;
			if (parameter.shadowColor == null) {
				if (other.shadowColor != null)
					return false;
			} else if (!parameter.shadowColor.equals(other.shadowColor))
				return false;
			if (parameter.shadowGlyphCount != other.shadowGlyphCount)
				return false;
			if (parameter.shadowOffsetX != other.shadowOffsetX)
				return false;
			if (parameter.shadowOffsetY != other.shadowOffsetY)
				return false;
			if (parameter.size != other.size)
				return false;
			return true;
		}
	}

	private static class Tuple<A, B> {
		A a;
		B b;

		public Tuple(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}
}