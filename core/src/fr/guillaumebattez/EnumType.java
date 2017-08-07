package fr.guillaumebattez;

import com.badlogic.gdx.graphics.Color;

enum EnumType
{
	NORMAL(0),
	GRASS(10),
	FIRE(7),
	WATER(4),
	ELECTRIC(5),
	ICE(8),
	FIGHTING(2),
	POISON(11),
	GROUND(14),
	FLYING(17),
	PSYCHIC(12),
	BUG(9),
	ROCK(13),
	GHOST(15),
	DRAGON(3),
	DARK(16),
	STEEL(1),
	FAIRY(6),
	NONE(18);

	private int textureIndex;

	EnumType(int textureIndex)
	{
		this.textureIndex = textureIndex;
	}

	public int getTextureIndex()
	{
		return textureIndex;
	}

	static EnumType[] getImmunities(EnumType type)
	{
		switch(type)
		{
			case NORMAL : return new EnumType[]{GHOST};
			case FLYING : return new EnumType[]{GROUND};
			case GROUND : return new EnumType[]{ELECTRIC};
			case GHOST : return new EnumType[]{NORMAL, FIGHTING};
			case STEEL : return new EnumType[]{POISON};
			case DARK : return new EnumType[]{PSYCHIC};
			case FAIRY : return new EnumType[]{DRAGON};

			default : return new EnumType[]{};
		}
	}

	static EnumType[] getResistances(EnumType type)
	{
		switch(type)
		{
			case GRASS : return new EnumType[]{GRASS, WATER, ELECTRIC, GROUND};
			case FIRE : return new EnumType[]{FIRE, GRASS, ICE, STEEL, BUG, FAIRY};
			case WATER : return new EnumType[]{FIRE, WATER, ICE, STEEL};
			case ELECTRIC : return new EnumType[]{ELECTRIC, FLYING, STEEL};
			case ICE : return new EnumType[]{ICE};
			case FIGHTING : return new EnumType[]{BUG, ROCK, DARK};
			case POISON : return new EnumType[]{GRASS, FIGHTING, POISON, BUG, FAIRY};
			case GROUND : return new EnumType[]{POISON, ROCK};
			case FLYING : return new EnumType[]{GRASS, FIGHTING, BUG};
			case PSYCHIC : return new EnumType[]{FIGHTING, PSYCHIC};
			case BUG : return new EnumType[]{GRASS, FIGHTING, GROUND};
			case ROCK : return new EnumType[]{NORMAL, FIRE, POISON, FLYING};
			case GHOST : return new EnumType[]{POISON, BUG};
			case DRAGON : return new EnumType[]{GRASS, FIRE, WATER, ELECTRIC};
			case DARK : return new EnumType[]{GHOST, DARK};
			case STEEL : return new EnumType[]{NORMAL, GRASS, ICE, FLYING, PSYCHIC, BUG, ROCK, DRAGON, STEEL, FAIRY};
			case FAIRY : return new EnumType[]{FIGHTING, BUG, DARK};

			default : return new EnumType[]{};
		}
	}

	static EnumType[] getWeaknesses(EnumType type)
	{
		switch(type)
		{
			case NORMAL : return new EnumType[]{FIGHTING};
			case GRASS : return new EnumType[]{FIRE, ICE, POISON, FLYING, BUG};
			case FIRE : return new EnumType[]{WATER, GROUND, ROCK};
			case WATER : return new EnumType[]{GRASS, ELECTRIC};
			case ELECTRIC : return new EnumType[]{GROUND};
			case ICE : return new EnumType[]{FIRE, FIGHTING, ROCK, STEEL};
			case FIGHTING : return new EnumType[]{FLYING, PSYCHIC, FAIRY};
			case POISON : return new EnumType[]{GROUND, PSYCHIC};
			case GROUND : return new EnumType[]{GRASS, WATER, ICE};
			case FLYING : return new EnumType[]{ELECTRIC, ICE, ROCK};
			case PSYCHIC : return new EnumType[]{BUG, GHOST, DARK};
			case BUG : return new EnumType[]{FIRE, FLYING, ROCK};
			case ROCK : return new EnumType[]{GRASS, WATER, FIGHTING, GROUND};
			case GHOST : return new EnumType[]{GHOST, DARK};
			case DRAGON : return new EnumType[]{ICE, DRAGON, FAIRY};
			case DARK : return new EnumType[]{FIGHTING, BUG, FAIRY};
			case STEEL : return new EnumType[]{FIRE, FIGHTING, GROUND};
			case FAIRY : return new EnumType[]{POISON, STEEL};

			default : return new EnumType[]{};
		}
	}


	public static float getDamageMultiplier(EnumType attacker, EnumType... receivers)
	{
		float damageMultiplier = 1;

		for(EnumType receiver : receivers)
		{
			for(EnumType immuneTo : getImmunities(receiver))
			{
				if(immuneTo == attacker)
					damageMultiplier *= 0;
			}

			for(EnumType resistantTo : getResistances(receiver))
			{
				if(resistantTo == attacker)
					damageMultiplier *= 0.5;
			}

			for(EnumType weakTo : getWeaknesses(receiver))
			{
				if(weakTo == attacker)
					damageMultiplier *= 2;
			}
		}

		return damageMultiplier;
	}
}