package fr.guillaumebattez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends ApplicationAdapter
{

    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 1920;
    public static TypeButton typeButton1;
    public static TypeButton typeButton2;
    public static boolean isType2Selected;

    private boolean prevIsType2Selected;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private Viewport gamePort;
	private Stage stage;

    private TypeButton[] typeButtonInputs = new TypeButton[18];
    private static EffectivenessCell[] effectivenessCells = new EffectivenessCell[18];

    private float resetCursorTimer;
    private float cursorX;
    private Texture cursorTexture;

	@Override
	public void create ()
	{
        this.batch = new SpriteBatch();
        this.cursorTexture = new Texture(Gdx.files.internal("cursor.png"));
        this.camera = new OrthographicCamera();
        this.shapeRenderer = new ShapeRenderer();
		this.gamePort = new FitViewport(V_WIDTH, V_HEIGHT, camera);
        this.camera.translate(V_WIDTH/2, V_HEIGHT/2);
        typeButton1 = new TypeButton(EnumType.NONE, EnumButtonMode.DISPLAYER);
        typeButton2 = new TypeButton(EnumType.NONE, EnumButtonMode.DISPLAYER);

		this.stage = new Stage(this.gamePort);
        Table inputTable = new Table();
        inputTable.bottom();
        inputTable.setFillParent(true);

        //INPUTS
        int index = 0;
        for(EnumType type : EnumType.values())
        {
            if(type == EnumType.NONE)
                continue;

            this.typeButtonInputs[index] = new TypeButton(type, EnumButtonMode.INPUT);
            inputTable.add(typeButtonInputs[index]).expandX();

            if(index % 3 == 2)
                inputTable.row();
            index ++;
        }
        index = 0;

        //POKEMON TYPE
        Table pokemonTypeTable = new Table();
        pokemonTypeTable.top();
        pokemonTypeTable.setFillParent(true);
        pokemonTypeTable.add(typeButton1).expandX();
        pokemonTypeTable.add(typeButton2).expandX();
        pokemonTypeTable.padTop(2);

        Table attackingTable = new Table();
        attackingTable.top();
        attackingTable.setFillParent(true);
        attackingTable.padLeft(Main.V_WIDTH/9);

        Table effectivenessTable = new Table();
        effectivenessTable.top();
        effectivenessTable.setFillParent(true);

        //ATTACKING TYPES AND EFFECTIVENESS
        for(EnumType attackingType : EnumType.values())
        {
            if(attackingType == EnumType.NONE)
                continue;

            TypeButton typeButton = new TypeButton(attackingType, EnumButtonMode.DISPLAYERSMALL);
            attackingTable.add(typeButton).padTop(Main.V_HEIGHT / 8 + 5);

            effectivenessCells[index] = new EffectivenessCell();
            effectivenessTable.add(effectivenessCells[index]).padTop(Main.V_HEIGHT / 8 + typeButton.getHeight() + 10);
            index ++;
        }

        Gdx.input.setInputProcessor(this.stage);
        this.stage.addActor(pokemonTypeTable);
        this.stage.addActor(attackingTable);
        this.stage.addActor(effectivenessTable);
        this.stage.addActor(inputTable);
	}


    @Override
	public void render()
	{
		super.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.shapeRenderer.setProjectionMatrix(camera.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(new Color(0.35f, 0.4f, 0.4f, 1));
        this.shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        this.shapeRenderer.end();

        this.batch.setProjectionMatrix(camera.combined);
        this.stage.draw();

        if(isType2Selected)
        {
            this.resetCursorTimer += Gdx.graphics.getDeltaTime();
            if(this.resetCursorTimer > 5)
            {
                isType2Selected = false;
            }
        }
        if(prevIsType2Selected != isType2Selected)
        {
            this.resetCursorTimer = 0;
            prevIsType2Selected = isType2Selected;
        }

        this.cursorX = MathUtils.lerp(this.cursorX, isType2Selected ? Main.V_WIDTH*0.75f - this.cursorTexture.getWidth()/2 : Main.V_WIDTH*0.25f - this.cursorTexture.getWidth()/2, 0.15f);

        this.batch.begin();
        this.batch.draw(this.cursorTexture, this.cursorX, Main.V_HEIGHT - this.cursorTexture.getHeight());
        this.batch.end();
	}

	public static void updateArray()
	{
        int index = 0;

        for(EnumType attackingType : EnumType.values())
        {
            if(attackingType == EnumType.NONE)
                continue;

            effectivenessCells[index].setEffectiveness(EnumType.getDamageMultiplier(attackingType, typeButton1.getEnumType(), typeButton2.getEnumType()));
            index++;
        }
	}

	@Override
	public void resize(int width, int height)
	{
        this.gamePort.update(width, height);
	}

	@Override
	public void dispose()
    {
        typeButton1.dispose();
        typeButton2.dispose();
        this.batch.dispose();
		this.shapeRenderer.dispose();
	    this.stage.dispose();
        this.cursorTexture.dispose();
        for(int i = 0; i < 18; i++)
        {
            effectivenessCells[i].dispose();
            this.typeButtonInputs[i].dispose();
        }
    }
}
