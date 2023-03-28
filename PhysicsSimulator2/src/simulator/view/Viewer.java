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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;

@SuppressWarnings("serial")
class Viewer extends SimulationViewer {

	private static final int _WIDTH = 500;
	private static final int _HEIGHT = 500;

	// (_centerX,_centerY) is used as the origin when drawing
	// the bodies
	private int _centerX;
	private int _centerY;

	// values used to shift the actual origin (the middle of
	// the window), when calculating (_centerX,_centerY)
	private int _originX = 0;
	private int _originY = 0;

	// the scale factor, used to reduce the bodies coordinates
	// to the size of the component
	private double _scale = 1.0;

	// indicates if the help message should be shown
	private boolean _showHelp = true;

	// indicates if the position/velocity vectors should be shown
	private boolean _showVectors = true;

	// the list bodies and groups
	private List<Body> _bodies;
	private List<BodiesGroup> _groups;

	// a color generator, and a map that assigns colors to groups
	private ColorsGenerator _colorGen;
	private Map<String, Color> _gColor;

	// the index and Id of the selected group, -1 and null means all groups
	private int _selectedGroupIdx = -1;
	private String _selectedGroup = null;

	Viewer() {
		initGUI();
	}

	private void initGUI() {

		// add a border
		setBorder(BorderFactory.createLineBorder(Color.black, 2));

		// initialize the color generator, and the map, that we use
		// assign colors to groups
		_colorGen = new ColorsGenerator();
		_gColor = new HashMap<>();

		// initialize the lists of bodies and groups
		_bodies = new ArrayList<>();
		_groups = new ArrayList<>();

		// The preferred and minimum size of the components
		setMinimumSize(new Dimension(_WIDTH, _HEIGHT));
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));

		// add a key listener to handle the user actions
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				switch(e.getKeyChar()) {
				case 'j': 
					_originX += 10;
					_originY += -10;
					break;
				case 'l': 
					_originX += 10;
					_originY += -10;
					break;
				case 'i':
					_originX += 10;
					_originY += -10;
					break;
				case 'm':
					_originX += 10;
					_originY += -10;
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
				case 'g': //-> PREGUNTAR SI ESTA PARTE ESTA BIEN;
					_selectedGroupIdx++; //-> PONEMOS EL ID A 1;
					if(_selectedGroupIdx == _groups.size()) { //-> SI ES IGUAL A GROUP.SIZE LO CAMBIAMOS A -1;
						_selectedGroupIdx = -1; //-> PARA COMENZAR DE NUEVO 
					}
					//PREGUNTAR QUE SI SHOWBODIES ES IGUAL QUE DRAWBODIES
					break;
				}
				repaint();
				
				/* CORREGIR CON PABLO:
				 * ES: gestionar la tecla 'g' de manera que haga visible el siguiente grupo.
				 * Tenga en cuenta que después del último grupo, se muestran todos los cuerpos.
				 * Esto se puede hacer modificando _selectedGroupIdx de -1 (todos los grupos) a
				 * _groups.size()-1 de forma circular. Cuando su valor es -1, _selectedGroup
				 * sería nulo, de lo contrario, sería el id del grupo correspondiente. En el
				 * método showBodies, solo dibujarás los que pertenecen al grupo seleccionado.
				 * 
				 */
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					repaint();
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					repaint();
					break;
				case '=':
					autoScale();
					repaint();
					break;

				default:
				}
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

		// a better graphics object
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// calculate the center
		_centerX = getWidth() / 2 - _originX;
		_centerY = getHeight() / 2 - _originY;

		//LA CURZ ROJA DEL CENTRO 
		gr.setColor(Color.RED);
		// LINEA VERTICAL 
	    g.drawLine(_originX, -_centerY, _originX, _centerY);
	    // LINEA HORIZONTAL 
	    g.drawLine(-_centerX, _originY, _centerX, _originY);
		
		
		// draw bodies
		drawBodies(gr);

		// show help if needed
		if (_showHelp) {
			showHelp(gr);
		}
	}
	
	private String getRatio() {
		return "Scaling ratio: " + this._scale;
	}
	
	private void showHelp(Graphics2D g) {
		/* ESTO ES LO QUE SE MUESTRA ARRIBA EN ROJO EM LA ESQUINA SUPERIOR IZQUIERDA;
		 * h: toggle help, v: toggle vectors, +: zoom-in, -: zoom-out, =: fit 
		 * l: move right, j: move left, i: move up, m: move down: k: reset 
		 * g: show next group
		 * Scaling ratio: ... 
		 * Selected Group: ...
		 */
		String _help = "h: toggle help, v: toggle vectors, +: zoom-in, -: zoom-out, =: fit \r\n"
				+ "		l: move right, j: move left, i: move up, m: move down: k: reset \r\n"
				+ "		g: show next group";
		g.drawString(_help, 20, getHeight() - (getHeight() - 25));
		g.drawString(getRatio(), 20, getHeight() - (getHeight() - 40));
		String group = "Selected Group: ";
		g.drawString(group, 20, getHeight() - (getHeight() - 40));
	}
	
	private void showBodies() {
		 
	}
	
	private void drawBodies(Graphics2D g) {
		/*
		 * TODO
		 * ES: Dibuja todos los cuerpos para los que isVisible(b) devuelve 'true' (ver
		 * isVisible abajo, devuelve 'true' si el cuerpo pertenece al grupo
		 * seleccionado). Para cada cuerpo, debes dibujar los vectores de velocidad y
		 * fuerza si _showVectors es 'true'. Usa el método drawLineWithArrow para
		 * dibujar los vectores. El color del cuerpo 'b' debe ser
		 * _gColor.get(b.getgId()) -- ver el método addGroup. Como punto de origen usar
		 * (_centerX,_centerY), y recordar dividir las coordenadas del cuerpo por el
		 * valor de _scale.
		 * 
		 */
		
		for(Body b: _bodies) {
			if(isVisible(b)) {
				Color color = _gColor.get(b.getgId());
				g.setColor(color);
				//DIBUJAR EL CUERPO; NO SE QUE FUNCION USAR PORQUE NO HAY B.DRAW;
				if(_showVectors == true) {
					Vector2D velocidad = b.getVelocity();
					Vector2D fuerza = b.getForce();
				}
				
				//COMO OBTENGO LOS PARAMETROS DE LA FUNCION DRAWLINE?
				//DRAWLINEWITHARROW() PARA LA FUERZA
				//DRAWLINEWITHARROW() PARA LA VELOCIDAD
			}
		} 
		
	}

	private boolean isVisible(Body b) {
		if(_selectedGroup == null || _selectedGroup == b.getgId()) {
			return true;
		}
		return false;
	}

	// calculates a value for scale such that all visible bodies fit in the window
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
		for(Body b: g._bodiesRO) { //-> _BODIESRO ES LA LISTA DE BODIES;
			_bodies.add(b);
		}
		_groups.add(g); //-> AÑADIR G A _GROUPS;
		_gColor.put(g.getId(), _colorGen.nextColor()); // assign color to group
		autoScale();
		update();
	}

	@Override
	public void addBody(Body b) {
		_bodies.add(b); //-> AÑADIR B A _BODIES
		autoScale();
		update();
	}

	@Override
	public void reset() {
		_groups.clear();
		_bodies.clear();
		_gColor.clear();
		_colorGen.reset(); // reset the color generator
		_selectedGroupIdx = -1;
		_selectedGroup = null;
		update();
	}

	@Override
	void update() {
		repaint();
	}

	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(Graphics g, int x1, int y1, int x2, int y2, int w, int h, Color lineColor, Color arrowColor) {

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
