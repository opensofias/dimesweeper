package dimesweeper.positions;

import java.util.Objects;

/**
 * Created by EDave on 25.05.2015.
 */
public abstract class Position {
	public static Position create (int i) {
		return new PositionImpl (i, Position.NIL);
	}

	public abstract int getHead ();

	public abstract Position getTail ();

	public abstract boolean isEmpty ();

	public abstract boolean equals (Position pos);

	public abstract int hashCode ();

	public Position prepend (int newHead) {
		return new PositionImpl (newHead, this);
	}

	public abstract int getLength ();

	@Override
	public boolean equals (Object o) {
		return o instanceof Position && equals ((Position) o);
	}

	public static final Position NIL = new Position () {
		@Override
		public int getHead () {
			throw new IndexOutOfBoundsException ("cannot get head of NIL");
		}

		@Override
		public Position getTail () {
			throw new IndexOutOfBoundsException ("cannot get tail of NIL");
		}

		@Override
		public boolean isEmpty () {
			return true;
		}

		@Override
		public int getLength () {
			return 0;
		}

		@Override
		public boolean equals (Position pos) {
			return this == pos;
		}

		@Override
		public int hashCode () {
			return 0;
		}
	};

	private static class PositionImpl extends Position {
		private final int head;
		private final Position tail;
		private final int length;

		public PositionImpl (int head, Position tail) {
			this.head = head;
			this.tail = tail;
			this.length = tail.getLength () + 1;
		}

		@Override
		public int getHead () {
			return head;
		}

		@Override
		public Position getTail () {
			return tail;
		}

		@Override
		public boolean isEmpty () {
			return false;
		}

		@Override
		public int getLength () {
			return length;
		}

		@Override
		public boolean equals (Position pos) {
			return pos instanceof PositionImpl && equals ((PositionImpl) pos);
		}

		public boolean equals (PositionImpl pos) {
			return head == pos.head && tail.equals (pos.tail);
		}

		@Override
		public int hashCode () {
			return Objects.hash (head, tail);
		}
	}
}
