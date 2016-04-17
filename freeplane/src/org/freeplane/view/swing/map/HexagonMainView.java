package org.freeplane.view.swing.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

import org.freeplane.features.nodestyle.NodeStyleModel.Shape;

@SuppressWarnings("serial")
public class HexagonMainView extends VariableInsetsMainView {
	private static final double HORIZONTAL_MARGIN_FACTOR = (Math.sqrt(3) + 1)/2;
	/**
	 * 
	 */
	public HexagonMainView() {
        super();
    }

	protected double getVerticalMarginFactor() {
		return 1.0;
	}
	
	@Override
	protected int getMinimumHorizontalInset() {
		return 3;
	}

	@Override
	protected int getMinimumVerticalInset() {
		return 3;
	}

	protected double getHorizontalMarginFactor() {
		return HORIZONTAL_MARGIN_FACTOR;
	}
	
	@Override
	protected void paintNodeShape(final Graphics2D g) {
		Polygon polygon = getPaintedShape();
		g.draw(polygon);
	}

	@Override
	protected void paintBackground(final Graphics2D graphics, final Color color) {
		graphics.setColor(color);
		graphics.fill(getPaintedShape());
	}
	private static final double HEIGHT_TO_WIDTH_RELATION = Math.sqrt(3)/2;

	@Override
    public
    Shape getShape() {
		return Shape.hexagon;
	}

	@Override
	public Dimension getPreferredSize() {
		if (isPreferredSizeSet()) {
			return super.getPreferredSize();
		}
		final Dimension prefSize = getPreferredSizeWithoutMargin(getMaximumWidth());
		int w = prefSize.width;
		int h = prefSize.height;
		int diameter = (int)(Math.ceil(Math.sqrt(w * w + h * h))) ;
		prefSize.width = (int) Math.ceil(Math.max(diameter, prefSize.width + getZoom() * getMinimumHorizontalInset()));
		prefSize.height = (int) Math.ceil(Math.max(diameter, prefSize.height + getZoom() * getMinimumVerticalInset()));
		if(prefSize.width < getMinimumWidth())
			prefSize.width = getMinimumWidth();
		if (prefSize.height < prefSize.width * HEIGHT_TO_WIDTH_RELATION)
			prefSize.height = (int) (prefSize.width * HEIGHT_TO_WIDTH_RELATION);
		else
			prefSize.width = (int) (prefSize.height / HEIGHT_TO_WIDTH_RELATION);
		return prefSize;
	}

	protected Polygon getPaintedShape() {
		int[] xCoords = new int[]{0,   getWidth()/4, 3 * getWidth() /4 , getWidth(),      3 * getWidth() / 4, getWidth() / 4};
		int[] yCoords = new int[]{getHeight() / 2, 0,  0,  getHeight() / 2, getHeight() - 1, getHeight() - 1};
		Polygon polygon = new Polygon(xCoords, yCoords, xCoords.length);
		return polygon;
	}
}
