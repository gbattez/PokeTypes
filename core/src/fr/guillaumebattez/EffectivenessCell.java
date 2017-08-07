package fr.guillaumebattez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by PcFixeGuillaume on 07/08/2017.
 */

public class EffectivenessCell extends Actor
{
    private Texture texture;
    private float effectiveness;

    public EffectivenessCell()
    {
        this.setWidth(Main.V_WIDTH/18);
        this.texture = new Texture(Gdx.files.internal("multipliers.png"));
        this.setHeight(texture.getHeight()*1.1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        int texIndex = 0;

        if(this.effectiveness == 0)
        {
            texIndex = 0;
        } else if(this.effectiveness == 0.25f)
        {
            texIndex = 1;
        } else if(this.effectiveness == 0.5f)
        {
            texIndex = 2;
        } else if(this.effectiveness == 1)
        {
            texIndex = 3;
        } else if(this.effectiveness == 2)
        {
            texIndex = 4;
        } else if(this.effectiveness == 4)
        {
            texIndex = 5;
        }

        batch.draw(texture, getX(), getY(), getWidth(), getHeight(), texIndex*60, 0, 60, texture.getHeight(), false, false);
    }

    public void dispose()
    {
        this.texture.dispose();
    }

    public void setEffectiveness(float effectiveness)
    {
        this.effectiveness = effectiveness;
    }
}
