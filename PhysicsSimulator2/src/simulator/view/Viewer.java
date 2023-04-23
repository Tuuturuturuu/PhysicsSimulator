package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;

import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;

@SuppressWarnings("serial")
class Viewer extends SimulationViewer {

	private static final int _WIDTH = 500;
	private static final int _HEIGHT = 500;

	private int _centerX;
	private int _centerY;

	private int _originX = 0;
	private int _originY = 0;

	private double _scale = 1.0;

	private boolean _showHelp = true;

	private boolean _showVectors = true;

	private List<Body> _bodies;
	private List<BodiesGroup> _groups;

	private ColorsGenerator _colorGen;
	private Map<String, Color> _gColor;

	private int _selectedGroupIdx = -1;
	private String _selectedGroup = null;

	Viewer() {
		initGUI();
	}

	private void initGUI() {

		setBorder(BorderFactory.createLineBorder(Color.black, 2));

		_colorGen = new ColorsGenerator();
		_gColor = new HashMap<>();

		_bodies = new ArrayList<>();
		_groups = new ArrayList<>();

		setMinimumSize(new Dimension(_WIDTH, _HEIGHT));
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'j':
					_originX += 10;

					break;
				case 'l':
					_originX -= 10;

					break;
				case 'i':

					_originY += 10;
					break;
				case 'm':

					_originY -= 10;
					break;
				case 'h':
					_showHelp = !_showHelp;
					break;
				case 'v':
					_showVectors = !_showVectors;
					break;
				case 'k':
					_originX = 0;
					_originY = 0;
					break;
				case 'g':

					_selectedGroupIdx++;
					if (_selectedGroupIdx == _groups.size()) {
						_selectedGroupIdx = -1;
						_selectedGroup = null;
					} else
						_selectedGroup = _groups.get(_selectedGroupIdx).getId();

					break;
				}
				repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale *= 1.1;
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					break;
				case '=':
					autoScale();
					break;

				default:
				}
				repaint();
			}
		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gr = (Graphics2D) g;
		gr.setPaint(Color.BLACK);
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		_centerX = getWidth() / 2 - _originX;
		_centerY = getHeight() / 2 - _originY;

		gr.setColor(Color.RED);

		Line2D lv = new Line2D.Float(_centerX, _centerY - 5, _centerX, _centerY + 5);
		gr.draw(lv);

		Line2D lh = new Line2D.Float(_centerX - 5, _centerY, _centerX + 5, _centerY);
		gr.draw(lh);

		drawBodies(gr);

		if (_showHelp) {
			showHelp(gr);
		}
	}

	private String getRatio() {
		return "Scaling ratio: " + this._scale;
	}

	private void showHelp(Graphics2D g) {

		g.setColor(Color.RED);
		g.drawString("h: toggle help, v: toggle vectors, +: zoom-in, -: zoom-out, =: fit \r\n", 10, 15);
		g.drawString("g: show next group", 10, 35);
		g.drawString("l: move right, j: move left, i: move up, m: move down: k: reset \r\n", 10, 55);
		g.drawString(getRatio(), 10, 75);

		g.setColor(Color.BLUE);
		if (_selectedGroup == null)
			g.drawString("Selected Group: all", 10, 95);
		else
			g.drawString("Selected Group: " + _selectedGroup, 10, 95);
	}

	private void drawBodies(Graphics2D g) {

		for (Body b : _bodies) {
			if (isVisible(b)) {

				Vector2D p = b.getPosition();
				int x = _centerX + (int) (p.getX() / _scale);
				int y = _centerY - (int) (p.getY() / _scale);

				if (_showVectors) {
					Vector2D v = b.getVelocity();
					Vector2D f = b.getForce();

					drawLineWithArrow((Graphics) g, x, y, x + (int) (v.direction().getX() * 25),
							y - (int) (v.direction().getY() * 25), 4, 4, Color.GREEN, Color.GREEN);

					drawLineWithArrow((Graphics) g, x, y, x + (int) (f.direction().getX() * 25),
							y - (int) (f.direction().getY() * 25), 4, 4, Color.RED, Color.RED);

				}

				g.setColor(_gColor.get(b.getgId()));
				g.fillOval(x - 5, y - 5, 10, 10);

				g.setColor(Color.BLACK);
				g.drawString(b.getId(), x - 7, y - 12);
			}
		}
	}

	private boolean isVisible(Body b) {
		return _selectedGroup == null || _selectedGroup.equals(b.getgId());
	}

	private void autoScale() {

		double max = 1.0;

		for (Body b : _bodies) {
			Vector2D p = b.getPosition();
			max = Math.max(max, Math.abs(p.getX()));
			max = Math.max(max, Math.abs(p.getY()));
		}

		double size = Math.max(1.0, Math.min(getWidth(), getHeight()));

		_scale = max > size ? 4.0 * max / size : 1.0;
	}

	@Override
	public void addGroup(BodiesGroup g) {
		_groups.add(g);
		for (Body b : g._bodiesRO) {
			_bodies.add(b);
		}

		_gColor.put(g.getId(), _colorGen.nextColor());
		autoScale();
		update();
	}

	@Override
	public void addBody(Body b) {
		_bodies.add(b);
		autoScale();
		update();
	}

	@Override
	public void reset() {
		_groups.clear();
		_bodies.clear();
		_gColor.clear();
		_colorGen.reset();
		_selectedGroupIdx = -1;
		_selectedGroup = null;
		update();
	}

	@Override
	void update() {
		repaint();
	}

	private void drawLineWithArrow(Graphics g, int x1, int y1, int x2, int y2, int w, int h, Color lineColor,
			Color arrowColor) {

		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}

}
