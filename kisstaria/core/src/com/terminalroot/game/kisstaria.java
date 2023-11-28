package com.terminalroot.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class kisstaria extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, jogador, jogador2;
	private Sprite jogadorSprite, jogadorSprite2;
	private float posicaoX, posicaoY, velocidade, posicaoX2, posicaoY2;


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("inicio.png");
		jogador = new Texture("jogador.png");
		jogadorSprite = new Sprite(jogador);
		posicaoX = 0;
		posicaoY = 0;
		velocidade = 400;

		jogador = new Texture("jogador2.png");
		jogadorSprite2 = new Sprite(jogador);
		posicaoX2 = posicaoX;
		posicaoY2 = posicaoY;


	}

	@Override
	public void render () {
		this.moveJogador();
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		batch.draw(img, 0, 0);
		batch.draw(jogadorSprite, posicaoX, posicaoY);
		batch.draw(jogadorSprite2, posicaoX2, posicaoY2);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		jogador.dispose();
	}
	private void moveJogador() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (posicaoX > 0){
			posicaoX -= velocidade * Gdx.graphics.getDeltaTime();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (posicaoX < Gdx.graphics.getWidth() - jogadorSprite.getWidth()) {
				posicaoX += velocidade * Gdx.graphics.getDeltaTime();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (posicaoY < Gdx.graphics.getHeight() - jogadorSprite.getHeight()){
			posicaoY += velocidade * Gdx.graphics.getDeltaTime();
		}
	}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			if (posicaoY > 0){
			posicaoY -= velocidade * Gdx.graphics.getDeltaTime();
			}
		}

	}
}
