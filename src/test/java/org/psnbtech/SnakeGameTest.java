package org.psnbtech;

import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Random;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SnakeGameTest{
	private SnakeGame snake;
	
	Method uSnake;
	Method uGame;
	
	Field newGame;
	Field snakefield;
	Field direct;
	Field time;
	Field random;
	Field over;
	Field pause;
	
	@Before
	public void setUp() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException{
		
		Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		snake = constructor.newInstance();
		
		newGame = snake.getClass().getDeclaredField("isNewGame");
		newGame.setAccessible(true);
		newGame.setBoolean(snake, true);
		
		snakefield = snake.getClass().getDeclaredField("snake");
		snakefield.setAccessible(true);
		snakefield.set(snake, new LinkedList<>());
		
		direct = snake.getClass().getDeclaredField("directions");
		direct.setAccessible(true);
		direct.set(snake, new LinkedList<>());
		
		time = snake.getClass().getDeclaredField("logicTimer");
		time.setAccessible(true);
		time.set(snake, new Clock(9.0f));
		
		random = snake.getClass().getDeclaredField("random");
		random.setAccessible(true);
		random.set(snake, new Random());
		
		over = snake.getClass().getDeclaredField("isGameOver");
		over.setAccessible(true);
		
		pause = snake.getClass().getDeclaredField("isPaused");
		pause.setAccessible(true);
		
		
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));	
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_ENTER, (char)13));
		
		uSnake = snake.getClass().getDeclaredMethod("updateSnake", new Class<?>[0]);
		uSnake.setAccessible(true);
		
		uGame = snake.getClass().getDeclaredMethod("updateGame", new Class<?>[0]);
		uGame.setAccessible(true);
		uGame.invoke(snake);
	}

	@After
	public void tearDown() throws IllegalArgumentException, IllegalAccessException {
		snake = null;
	}

	
	@Test
	public void updateSnake1() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		uSnake.invoke(snake);
	}
	
	@Test
	public void updateSnake2() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		uSnake.invoke(snake);
		uSnake.invoke(snake);
	}
	
	@Test
	public void updateSnake3() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		uSnake.invoke(snake);
		uSnake.invoke(snake);
	}
	
	
	
	@Test
	public void SnakeGame1() throws IllegalArgumentException, IllegalAccessException {//[1,7,11(�a \/ �b),22]
		newGame.setBoolean(snake, false);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_ENTER, (char)13));
	}
	@Test
	public void SnakeGame2() throws IllegalArgumentException, IllegalAccessException {//[1,7,11(�a \/ b),22]
		newGame.setBoolean(snake, false);
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_ENTER, (char)13));
	}
	@Test
	public void SnakeGame3() throws IllegalArgumentException, IllegalAccessException {//[1,7,11(a \/ �b),22]
		newGame.setBoolean(snake, true);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_ENTER, (char)13));
	}
	@Test
	public void SnakeGame4() throws IllegalArgumentException, IllegalAccessException {//[1,7,11,21,22]
		newGame.setBoolean(snake, true);
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_ENTER, (char)13));
	}
	
	
	//found pause bug here
	@Test
	public void SnakeGame5() throws IllegalArgumentException, IllegalAccessException {//[1,6,10,22]
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_P, (char)13));
	}
	@Test
	public void SnakeGame6() throws IllegalArgumentException, IllegalAccessException {//[1,6,10,20,22]
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_P, (char)13));
	}
	
	@Test
	public void SnakeGame7() throws IllegalArgumentException, IllegalAccessException {//[1,2,8(a /\ b),9,22]
		pause.setBoolean(snake, false);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
	}
	@Test
	public void SnakeGame7a() throws IllegalArgumentException, IllegalAccessException {//[1,2,8(�a /\ b),22]
		pause.setBoolean(snake, true);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
	}
	@Test
	public void SnakeGame7b() throws IllegalArgumentException, IllegalAccessException {//[1,2,8(a /\ �b),22]
		pause.setBoolean(snake, false);
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
	}
	@Test
	public void SnakeGame7c() throws IllegalArgumentException, IllegalAccessException {//[1,2,8(�a /\ �b),22]
		pause.setBoolean(snake, true);
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
	}
	
/*	
	@Test
	public void SnakeGame8() throws IllegalArgumentException, IllegalAccessException, InterruptedException {//[1,2,8(a /\ b),9,12,22]
		pause.setBoolean(snake, false);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		//snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		//snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		//snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
	}
	*/

	
	@Test //Test case [1,2,8 (a/\b),9,12,16,22]
	public void SnakeGame16() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, false);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		
	}
	
	@Test //Test case [1,2,8 (a/\�b),9,12,16,22]
	public void SnakeGame16a() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, false);
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
	}
	
	@Test //Test case [1,2,8 (a�/\b),9,12,16,22]
	public void SnakeGame16b() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, true);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
	}
	
	@Test //Test case [1,3,8 (a/\b),9,13,17,22]
	public void SnakeGame17a() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, false);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
	}
	
	@Test //Test case [1,3,8 (a/\�b),9,13,17,22]
	public void SnakeGame17b() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, false);
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
	}
	
	@Test //Test case [1,3,8 (a�/\b),9,13,17,22]
	public void SnakeGame17c() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, true);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
	}
	
	@Test //Test case [1,4,8 (a/\b),9,14,18,22]
	public void SnakeGame18a() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, false);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
	}
	
	@Test //Test case [1,4,8 (a/\�b),9,14,18,22]
	public void SnakeGame18b() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, false);
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
	}
	
	@Test //Test case [1,4,8 (a�/\b),9,14,18,22]
	public void SnakeGame18c() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, true);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
	}
	
	@Test //Test case [1,5,8 (a/\b),9,15,19,22]
	public void SnakeGame19a() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, false);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
	}
	
	@Test //Test case [1,5,8 (a/\�b),9,15,19,22]
	public void SnakeGame19b() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, false);
		over.setBoolean(snake, true);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
	}
	
	@Test //Test case [1,5,8 (a�/\b),9,15,19,22]
	public void SnakeGame19c() throws IllegalArgumentException, IllegalAccessException, InterruptedException {
		pause.setBoolean(snake, true);
		over.setBoolean(snake, false);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
	}
}	
/*	@Test
	public void updateSnake4() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		m.invoke(snake);
		m.invoke(snake);
		m.invoke(snake);
	}*/	
	/*@SuppressWarnings("static-access")
	@Test
	public void getDirectionTest() {
		//snake.addKeyListener(KeyEvent.VK_ENTER);
		//snake.main(new String[0]);
		//r.keyPress(KeyEvent.VK_P);
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_ENTER, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_S, (char)13));
		snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_D, (char)13));
		//snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_P, (char)13));
		//snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_W, (char)13));
		//assertNull(snake.getDirection());
	}
	*/
	
	//throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//instance = new CrashandBurn();aa
		//Constructor<SnakeGame> constructor = SnakeGame.class.getDeclaredConstructor();
		//assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		//constructor.newInstance();
		
		
		
		
		//byteArrayInputStream = new ByteArrayInputStream("W".getBytes());
		//System.setIn(byteArrayInputStream);
		//KeyEvent e = new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_LEFT, 'A');
		//provideInput("Enter");
		//provideInput("A");
		//Direction d = snake.getDirection();
		//Robot r = new Robot();
		//r.keyPress(KeyEvent.VK_ENTER);
		//KeyEvent enter = new KeyEvent(snake, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0 , KeyEvent.VK_ENTER, (char)13);
		//snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_ENTER, (char)13));
		
		
		//snake.dispatchEvent(new KeyEvent(snake, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0 , KeyEvent.VK_A, 'A'));
		
		//snake.dispatchEvent(new KeyEvent(KEY_PRESSED, {'key' : 'A'}));
		//assertEquals(Direction.North, snake.getDirection());
	
	
    //public static class CrashAndBurn extends SnakeGame {
      //  @Override
       // public static void main(String[] args){ 
       //     throw new Exception();
       // }
    //}

