package fr.guillaumebattez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by PcFixeGuillaume on 02/08/2017.
 */

public class TypeButton extends Actor
{
    private EnumType enumType;
    private float overlayFlashAlpha;
    private Texture texture;
    private EnumButtonMode buttonMode;

    public TypeButton(EnumType enumType, EnumButtonMode buttonMode)
    {
        this.texture = new Texture(Gdx.files.internal("pokeTypes.png"));
        switch (buttonMode)
        {
            case INPUT:
            {
                this.setWidth(Main.V_WIDTH / 3);
                this.setHeight(Main.V_HEIGHT / 12);
            } break;
            case DISPLAYER:
            {
                this.setWidth(Main.V_WIDTH / 2);
                this.setHeight(Main.V_HEIGHT / 8);
            } break;
            case DISPLAYERSMALL:
            {
                this.setWidth(Main.V_WIDTH/18);
                this.setHeight(Main.V_HEIGHT / 8);
            }
        }

        this.enumType = enumType;
        this.buttonMode = buttonMode;

        if(buttonMode == EnumButtonMode.INPUT)
        {
            addListener(new InputListener()
            {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    if(Main.isType2Selected)
                    {
                        if(((TypeButton) event.getTarget()).enumType == Main.typeButton1.enumType)
                            return false;

                        Main.typeButton2.enumType = ((TypeButton) event.getTarget()).enumType;
                        Main.typeButton2.overlayFlashAlpha = 0.8f;
                    } else
                    {

                        Main.typeButton1.enumType = ((TypeButton) event.getTarget()).enumType;
                        Main.typeButton1.overlayFlashAlpha = 0.8f;
                        Main.typeButton2.enumType = EnumType.NONE;
                    }
                    Main.isType2Selected = !Main.isType2Selected;
                    Main.updateArray();
                    return true;
                }
            });
        }
    }

    public EnumType getEnumType()
    {
        return enumType;
    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        if (buttonMode == EnumButtonMode.DISPLAYERSMALL)
        {
            batch.draw(texture, getX(), getY(), 0, 0, getHeight(), getWidth(), 1, 1, 90,
                    enumType.getTextureIndex()*350, 0, 350, texture.getHeight(), false, false);
        } else
        {
            if(buttonMode == EnumButtonMode.INPUT && (enumType == Main.typeButton1.enumType || enumType == Main.typeButton2.enumType))
                batch.setColor(Color.DARK_GRAY);


            batch.draw(texture, getX(), getY(), 0, 0, getWidth(), getHeight(), 1, 1, 0,
                    enumType.getTextureIndex()*350, 0, 350, texture.getHeight(), false, false);

            if(overlayFlashAlpha > 0)
            {
                batch.setColor(1, 1, 1, overlayFlashAlpha);
                batch.draw(texture, getX(), getY(), 0, 0, getWidth(), getHeight(), 1, 1, 0,
                        19*350, 0, 350, texture.getHeight(), false, false);
                overlayFlashAlpha -= Gdx.graphics.getDeltaTime()*2;
            }
            batch.setColor(Color.WHITE);
        }
    }

    public void dispose()
    {
        this.texture.dispose();
    }
}
