package ure.ui;

import ure.math.UColor;
import ure.render.URenderer;

import java.util.HashMap;

/**
 * UModal implements a pane of tiles to be rendered above a camera, and a command receiver
 * to intercept input.
 *
 * TODO: separate the concept to include modal UI elements like direction pickers which
 * don't necessarily render an opaque square of tiles.
 *
 */
public class UModal extends View {

    URenderer renderer;
    UCamera camera;
    public int width, height;
    public int pixelWidth, pixelHeight;
    public int cellx,celly;
    public UColor bgColor;
    HashMap<String,TextFrag> texts;

    class TextFrag {
        String name;
        String text;
        int row;
        int col;
        UColor color;

        public TextFrag(String tname, String ttext, int trow, int tcol, UColor tcolor) {
            name = tname;
            text = ttext;
            row = trow;
            col = tcol;
            color = tcolor;
        }
    }

    public UModal(int theCharWidth, int theCharHeight, URenderer theRenderer, UCamera theCamera, UColor thebgColor) {
        renderer = theRenderer;
        camera = theCamera;
        width = theCharWidth;
        height = theCharHeight;
        bgColor = thebgColor;
        texts = new HashMap<>();
        //pixelWidth = theRenderer.cellWidth() * width;
        //pixelHeight = theRenderer.cellHeight() * height;
        cellx = camera.getWidthInCells()/2 - width/2;
        celly = camera.getHeightInCells()/2 - height/2;
        //image = new BufferedImage(pixelWidth, pixelHeight, BufferedImage.TYPE_INT_RGB);
        //Graphics g = getGraphics();
        //g.setColor(Color.BLACK);
        //g.fillRect(0,0,pixelWidth,pixelHeight);
    }

    public static UModal popMessage(String message, URenderer theRenderer, UCamera theCamera, UColor thebgcolor) {
        UModal m = new UModal(message.length() + 4, 5, theRenderer, theCamera, thebgcolor);
        // TODO: Fix for new renderer
        //m.addText("message", message, 1, 1, theRenderer.UItextColor.makeAWTColor());
        return m;
    }
    //public Graphics getGraphics() { return image.getGraphics(); }

    @Override
    public void draw(URenderer renderer) {
        DrawFrame();
        DrawContent();
    }

    void DrawFrame() {
        // TODO: Fix for new renderer
        //renderer.renderUIFrame(this);
    }

    void DrawContent() {
        //Graphics g = getGraphics();
        for (String textName : texts.keySet()) {
            TextFrag frag = texts.get(textName);
            // TODO: Fix for new renderer
            //g.setFont(renderer.font);
            //g.setColor(frag.color);
            //g.drawString(frag.text, frag.row * renderer.cellWidth(), ((frag.col + 1) * renderer.cellHeight()) + 0);
        }
    }
    //public BufferedImage getImage() {
    //    return image;
    //}

    public void hearCommand(String command) {
        //Dismiss();
    }

    //void Dismiss() {
    //    camera.detachModal();
    //}

    public void addText(String name, String text, int row, int col) {
        // TODO: Fix for new renderer
        //addTextFrag(new TextFrag(name, text, row, col, renderer.UItextColor.makeAWTColor()));
    }
    public void addText(String name, String text, int row, int col, UColor color) {
        addTextFrag(new TextFrag(name, text, row, col, color));
    }
    void addTextFrag(TextFrag frag) {
        texts.put(frag.name, frag);
    }

}
