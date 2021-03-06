package eu.evensson.optpartsim.physics;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class Array2D<T> {

	private final Object[] array;
	private final int width;
	private final int height;

	public Array2D(final int width, final int height) {
		this.width = width;
		this.height = height;
		array = new Object[width * height];
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	@SuppressWarnings("unchecked")
	public T get(final int x, final int y) {
		return (T) array[getIndex(x, y)];
	}

	public void set(final int x, final int y, final T value) {
		array[getIndex(x, y)] = value;
	}

	public void forEach(final BiConsumer<Integer, Integer> consumer) {
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				consumer.accept(x, y);
			}
		}
	}

	private int getIndex(final int x, final int y) {
		return y * width + x;
	}

	// Generated code

	@Override
	public String toString() {
		return "Array2D [array=" + Arrays.toString(array) + ", width=" + width
				+ ", height=" + height + "]";
	}

}
