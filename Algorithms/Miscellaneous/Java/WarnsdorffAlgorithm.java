
// Warnsdorff's algorithm
import java.util.concurrent.ThreadLocalRandom;

class GFG
{
	public static final int N = 8;

	public static final int cx[] = {1, 1, 2, 2, -1, -1, -2, -2};
	public static final int cy[] = {2, -2, 1, -1, 2, -2, 1, -1};

	// the 8x8 chessboard
	boolean limits(int x, int y)
	{
		return ((x >= 0 && y >= 0) &&
				(x < N && y < N));
	}

	boolean isempty(int a[], int x, int y)
	{
		return (limits(x, y)) && (a[y * N + x] < 0);
	}


	int getDegree(int a[], int x, int y)
	{
		int count = 0;
		for (int i = 0; i < N; ++i)
			if (isempty(a, (x + cx[i]),
						(y + cy[i])))
				count++;

		return count;
	}

	Cell nextMove(int a[], Cell cell)
	{
		int min_deg_idx = -1, c,
			min_deg = (N + 1), nx, ny;


		int start = ThreadLocalRandom.current().nextInt(1000) % N;
		for (int count = 0; count < N; ++count)
		{
			int i = (start + count) % N;
			nx = cell.x + cx[i];
			ny = cell.y + cy[i];
			if ((isempty(a, nx, ny)) &&
				(c = getDegree(a, nx, ny)) < min_deg)
			{
				min_deg_idx = i;
				min_deg = c;
			}
s		}

		// IF we could not find a next cell
		if (min_deg_idx == -1)
			return null;

		// Store coordinates of next point
		nx = cell.x + cx[min_deg_idx];
		ny = cell.y + cy[min_deg_idx];

		// Mark next move
		a[ny * N + nx] = a[(cell.y) * N +
						(cell.x)] + 1;

		// Update next point
		cell.x = nx;
		cell.y = ny;

		return cell;
	}

	/* displays the chessboard with all the
	legal knight's moves */
	void print(int a[])
	{
		for (int i = 0; i < N; ++i)
		{
			for (int j = 0; j < N; ++j)
				System.out.printf("%d\t", a[j * N + i]);
			System.out.printf("\n");
		}
	}


	boolean neighbour(int x, int y, int xx, int yy)
	{
		for (int i = 0; i < N; ++i)
			if (((x + cx[i]) == xx) &&
				((y + cy[i]) == yy))
				return true;

		return false;
	}


	boolean findClosedTour()
	{
		// Filling up the chessboard matrix with -1's
		int a[] = new int[N * N];
		for (int i = 0; i < N * N; ++i)
			a[i] = -1;

		// initial position
		int sx = 3;
		int sy = 2;

		// Current points are same as initial points
		Cell cell = new Cell(sx, sy);

		a[cell.y * N + cell.x] = 1; // Mark first move.

		// Keep picking next points using
		// Warnsdorff's heuristic
		Cell ret = null;
		for (int i = 0; i < N * N - 1; ++i)
		{
			ret = nextMove(a, cell);
			if (ret == null)
				return false;
		}

		if (!neighbour(ret.x, ret.y, sx, sy))
			return false;

		print(a);
		return true;
	}

	// Input Code
	public static void main(String[] args)
	{
		// While we don't get a solution
		while (!new GFG().findClosedTour())
		{
			;
		}
	}
}

class Cell
{
	int x;
	int y;

	public Cell(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}

