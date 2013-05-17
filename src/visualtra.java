/***********************************************************************
    2013 (C) Alex Dobrianski TRAJECTORY visualization module-
    web page implementation (via java) TRA.EXE's work
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>

    Design and development by Team "Plan B" is licensed under 
    a Creative Commons Attribution-ShareAlike 3.0 Unported License.
    http://creativecommons.org/licenses/by-sa/3.0/ 
************************************************************************/


import java.applet.Applet;
import java.awt.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.util.Enumeration;
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;
import java.io.File;  
import java.io.InputStream;  
import java.net.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import java.awt.event.MouseEvent;
import com.sun.j3d.utils.geometry.ColorCube;
import java.awt.*;
//import java.applet.*; 
import java.awt.event.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import java.awt.image.BufferedImage;  


public class visualtra extends Applet  
implements ScaleChangeListener, RotationChangeListener, TranslationChangeListener, ActionListener, Runnable
{

    public String URLSOURCE = "http://192.168.0.102/SatCtrl";
    //public String URLSOURCE = "http://24.84.57.253/SatCtrl";
    private double GrStX[] = new double[10];
    private double GrStY[] = new double[10];
    private double GrStZ[] = new double[10];
    private int iGrstMax =0;
    public LineArray GrLines[]= new LineArray[10];
    
    private double CenterX;
    private double CenterY;
    private double CenterZ;

    
    private double EarthX;
    private double EarthY;
    private double EarthZ;
    private double EarthR;
    private double EarthRot;
    private double EarthRotOld;
    private double tCountEarthRot; // rotate onec per 10 iterations ????
    Texture texEarth;
    
    private double MoonX;
    private double MoonY;
    private double MoonZ;
    private double MoonR;
    private double MoonRot;
    private double MoonRotOld;
    Texture texMoon;
    
    private double SunX;
    private double SunY;
    private double SunZ;
    private double SunR;
    
    // Constants for type of light to use
    private static final int DIRECTIONAL_LIGHT = 0;
    private static final int POINT_LIGHT = 1;
    private static final int SPOT_LIGHT = 2;

    // Flag indicates type of lights: directional, point, or spot
    // lights.  This flag is set based on command line argument
    private static int lightType = POINT_LIGHT;

    private SimpleUniverse u = null;
    
    //Label m_RotationLabel = null;
    //TextField m_RotationFieldX = null;
    //TextField m_RotationFieldY = null;
    //TextField m_RotationFieldZ = null;
    //Label m_TranslationLabel = null;
    //TextField m_TranslationFieldX = null;
    //TextField m_TranslationFieldY = null;
    //TextField m_TranslationFieldZ = null;
    //Label m_ScaleLabel = null;
    //TextField m_ScaleFieldZ = null;
    //TextField m_ScaleFieldY = null;
    //TextField m_ScaleFieldX = null;
    //TextField m_TimerCtrl = null;
    int TimerCount = 0;
    private static int m_kWidth = 1400;
    private static int m_kHeight = 800;
    protected Bounds m_ApplicationBounds = null;
    protected BranchGroup m_SceneBranchGroup = null;
    //Button mLeft = null;
    Button m_EarthView = null;
    Button m_MoonView = null;
    
    Button m_ViewYZ = null;
    Button m_ViewXZ = null;
    Button m_ViewXY = null;
    
    
    boolean m_View_Earth = true;
    boolean ButtonPressed = false;
    private Thread XMLTimerread;
    public int time =1000;
    protected Vector3f m_OriginalPosition = null;
    protected Object m_Earth_Moon = null;
    protected Transform3D m_Transform3D = null;
    protected Transform3D m_TransformX = null;
    protected Transform3D m_TransformY = null;
    //protected Transform3D m_EarthRotationDelta = null;
    protected Transform3D m_EarthRotation = null;
    protected TransformGroup m_TransformGrEarthRotation = null;
    
    //protected Transform3D m_MoonRotationDelta = null;
    protected Transform3D m_MoonRotation = null;
    protected TransformGroup m_TransformGrMoonRotation = null;
    
    protected Transform3D m_Transform3DMoon = null;
    protected Transform3D m_Transform3DSun = null;
    

    //public LineArray MoonLines= null;
    
    private static final int VIEW_Z1 = 0;
    private static final int VIEW_Z2 = 1;
    private static final int VIEW_Z3 = 2;
    private static final int VIEW_Z4 = 3;
    int m_TypeOfoView = 0;
    double m_ScaleFromMouse = 1.0;
    
    
    public LineArray Sat0Lines= null;
    public LineArray Sat1Lines= null;
    public LineArray Sat2Lines= null;
    public LineArray Sat3Lines= null;
    public LineArray Sat4Lines= null;
    public LineArray Sat5Lines= null;
    public LineArray Sat6Lines= null;
    public LineArray Sat7Lines= null;
    public LineArray Sat8Lines= null;
    public LineArray Sat9Lines= null;
    
    public LineArray Sat0Dot =  null;
    public LineArray Sat1Dot= null;
    public LineArray Sat2Dot= null;
    public LineArray Sat3Dot= null;
    public LineArray Sat4Dot= null;
    public LineArray Sat5Dot= null;
    public LineArray Sat6Dot= null;
    public LineArray Sat7Dot= null;
    public LineArray Sat8Dot= null;
    public LineArray Sat9Dot= null;
    
    
    
    int MaxSat = 0;
    ImageComponent2D ImageSkyMap = null;
    Texture texSky;
    Shape3D Sat0Line = null;
    
    TransformGroup tgEarthRot = null;
    //BranchGroup MoonTra = null;  
    TransformGroup tgMoon = null;
    TransformGroup tgLtSun = null;
    BranchGroup SatTra0 = null;  
    BranchGroup SatTra1 = null;  
    BranchGroup SatTra2 = null;  
    BranchGroup SatTra3 = null;  
    BranchGroup SatTra4 = null;  
    BranchGroup SatTra5 = null;  
    BranchGroup SatTra6 = null;  
    BranchGroup SatTra7 = null;  
    BranchGroup SatTra8 = null;  
    BranchGroup SatTra9 = null;  
    double TimeJD =0;
    double TimeJDOld =0;
    Label m_LabelJD=null;
    String TimeYYMMDDHHMMSS = "  /  /     :  :  ";
    Label m_LabelYYMMDDHHMMSS=null;
    long delaymsec = 1000;
    long Olddelaymsec = 1000;
    long dMinFromNow = 3;
    
    
    
    //***********************************************
    protected TransformGroup getTransformEarthMoon()
    {
        if (m_Earth_Moon instanceof TransformGroup)
            return (TransformGroup) m_Earth_Moon;
        return null;
    }    
    public void actionPerformed(ActionEvent e) 
    {
        ButtonPressed = true;
        String szComand =e.getActionCommand();
        if (szComand.compareTo("Moon View") == 0)
        {
            m_View_Earth = false;
            // moving by vector 
            //Vector3f vector = new Vector3f(140f,10.1f , 10.4f);
            //TransformGroup tg = getTransformEarthMoon();
            //if (tg != null) 
            //{
            //    vector.scale(0.005f);
            //    Vector3d vTranslation = new Vector3d();
            //    tg.getTransform(m_Transform3D);
            //    m_Transform3D.get(vTranslation);
            //    vTranslation.x += vector.x;
            //    vTranslation.y += vector.y;
            //    vTranslation.z += vector.z;
            //    m_Transform3D.setTranslation(vTranslation);
            //    tg.setTransform(m_Transform3D);
            //}
            
            // scale objects
            //TransformGroup tg = getTransformEarthMoon();
            //if (tg != null) 
            //{
            //    tg.getTransform(m_Transform3D);
            //    Vector3d vScale = new Vector3d();
            //    m_Transform3D.getScale(vScale);
            //    Vector3f delta = new Vector3f();
            //    delta.x = 0.1f;
            //    Vector3d objectScale = new Vector3d(vScale.x + delta.x, vScale.x + delta.x, vScale.x + delta.x);
            //    m_Transform3D.setScale(objectScale);
            //    tg.setTransform(m_Transform3D);
            //}
            //Vector3f vector = new Vector3f(0f,0f,0f);
            TransformGroup tg = getTransformEarthMoon();
            if (tg != null) 
            {
                // earth in the center
                Vector3d vTranslation = new Vector3d(-(MoonX-CenterX)*m_ScaleFromMouse,-(MoonY-CenterY)*m_ScaleFromMouse,-(MoonZ-CenterZ)*m_ScaleFromMouse);
                m_Transform3D.setTranslation(vTranslation);
                // and scale set to original value
                //m_ScaleFromMouse = 1;
                Vector3d objectScale = new Vector3d(m_ScaleFromMouse, m_ScaleFromMouse, m_ScaleFromMouse);
                m_Transform3D.setScale(objectScale);
                tg.setTransform(m_Transform3D);
            }
        }
        if (szComand.compareTo("Earth View") == 0)
        {
            m_View_Earth = true;
            //Vector3f vector = new Vector3f(0f,0f,0f);
            TransformGroup tg = getTransformEarthMoon();
            if (tg != null) 
            {
                // earth in the center
                Vector3d vTranslation = new Vector3d(0,0,0);
                m_Transform3D.setTranslation(vTranslation);
                // and scale set to original value
                //m_ScaleFromMouse = 1;
                Vector3d objectScale = new Vector3d(m_ScaleFromMouse, m_ScaleFromMouse, m_ScaleFromMouse);
                m_Transform3D.setScale(objectScale);
                tg.setTransform(m_Transform3D);
            }
        }
        if (szComand.compareTo("Rot Z") == 0)  // negative
        {
            switch(m_TypeOfoView)
            {
             case VIEW_Z1:m_TypeOfoView = VIEW_Z2;break;
             case VIEW_Z2:m_TypeOfoView = VIEW_Z3;break;
             case VIEW_Z3:m_TypeOfoView = VIEW_Z4;break;
             case VIEW_Z4:m_TypeOfoView = VIEW_Z1;break;
            }
            if (m_View_Earth)
            {
                //Vector3f vector = new Vector3f(0f,0f,0f);
                TransformGroup tg = getTransformEarthMoon();
                if (tg != null) 
                {
                    // the center
                    Vector3d vTranslation = new Vector3d(0,0,0);
                    m_Transform3D.setTranslation(vTranslation);
                    // and scale set to original value
                    
                    Vector3d objectScale = new Vector3d(m_ScaleFromMouse, m_ScaleFromMouse, m_ScaleFromMouse);
                    m_Transform3D.setScale(objectScale);
                    //tg.setTransform(m_Transform3D);
                    double y_angle =  3.14159265358979323846264338327950288 /2.0;
                    //m_TransformY = new Transform3D();
                    m_TransformY.rotY(y_angle);
                    Matrix4d mat = new Matrix4d();
                    m_Transform3D.get(mat);
                    m_Transform3D.setTranslation(new Vector3d(0.0, 0.0, 0.0));
                    m_Transform3D.mul(m_Transform3D, m_TransformY);
                    Vector3d translation = new Vector3d(mat.m03, mat.m13, mat.m23);
                    m_Transform3D.setTranslation(translation);
                    tg.setTransform(m_Transform3D);
                }
            }
            else
            {
                //Vector3f vector = new Vector3f(0f,0f,0f);
                TransformGroup tg = getTransformEarthMoon();
                if (tg != null) 
                {
                    // the center
                    Vector3d vTranslation = new Vector3d(0,0,0);
                    m_Transform3D.setTranslation(vTranslation);
                    // and scale set to original value
                    Vector3d objectScale = new Vector3d(m_ScaleFromMouse, m_ScaleFromMouse, m_ScaleFromMouse);
                    m_Transform3D.setScale(objectScale);
                    //tg.setTransform(m_Transform3D);
                    double y_angle =  3.14159265358979323846264338327950288 /2.0;
                    //m_TransformY = new Transform3D();
                    m_TransformY.rotY(y_angle);
                    Matrix4d mat = new Matrix4d();
                    m_Transform3D.get(mat);
                    m_Transform3D.setTranslation(new Vector3d(0.0, 0.0, 0.0));
                    m_Transform3D.mul(m_Transform3D, m_TransformY);
                    Vector3d translation = new Vector3d(mat.m03, mat.m13, mat.m23);
                    m_Transform3D.setTranslation(translation);
                    switch(m_TypeOfoView)
                    {
                    case VIEW_Z1:m_Transform3D.setTranslation(new Vector3d(-(MoonX-CenterX),-(MoonY-CenterY),-(MoonZ-CenterZ)));break;
                    case VIEW_Z2:m_Transform3D.setTranslation(new Vector3d(-(MoonX-CenterX),(MoonY-CenterY),(MoonZ-CenterZ)));break;
                    case VIEW_Z3:m_Transform3D.setTranslation(new Vector3d((MoonX-CenterX),(MoonY-CenterY),-(MoonZ-CenterZ)));break;
                    case VIEW_Z4:m_Transform3D.setTranslation(new Vector3d(-(MoonX-CenterX),(MoonY-CenterY),-(MoonZ-CenterZ)));break;
                    }
                    
                    tg.setTransform(m_Transform3D);
                }
            }
        }
    }

    public BranchGroup createSceneGraph(SimpleUniverse u) 
    {

	Color3f lColor2   = new Color3f(0.0f, 0.0f, 1.0f);

        Color3f alColor   = new Color3f(.2f, .2f, .2f);
	Color3f bgColor   = new Color3f(0.05f, 0.05f, 0.2f);

	Transform3D t;

	// Create the root of the branch graph
	BranchGroup objRoot = new BranchGroup();

        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        TransformGroup objScale = new TransformGroup();
        Transform3D t3d = new Transform3D();
        //Vector3f vector = new Vector3f(-.2f,.1f , -.4f);
        t3d.setScale(0.8);
        //t3d.setTranslation(vector);
        objScale.setTransform(t3d);
        objRoot.addChild(objScale);

	// Create a bounds for the background and lights
	BoundingSphere bounds =  new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0);
        
        // Set up the background
	
                
        if (ImageSkyMap != null)
        {
            Background bg = new Background(bgColor);
            bg.setImage(ImageSkyMap);
            bg.setApplicationBounds(bounds);
            objScale.addChild(bg);
        }
        

	// Create the transform group node for the each light and initialize
	// it to the identity.  Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at runtime.  Add them to the root
	// of the subgraph.
        // mouse simple
       /*
        TransformGroup viewTransformGroup =
                u.getViewingPlatform().getViewPlatformTransform();
        KeyNavigatorBehavior keyInteractor =
                new KeyNavigatorBehavior(viewTransformGroup);
        BoundingSphere movingBounds = new BoundingSphere(new Point3d(0.0, 0.0,
        0.0), 10000.0);
        keyInteractor.setSchedulingBounds(movingBounds);
        objRoot.addChild(keyInteractor);
        MouseRotate behavior = new MouseRotate(); 
        behavior.setTransformGroup(viewTransformGroup); 
        objRoot.addChild(behavior); 
        behavior.setSchedulingBounds(bounds);
       */
        // Let Java 3D perform optimizations on this scene graph.
        objRoot.compile();

	return objRoot;
    }
    
    protected BranchGroup createSceneBranchGroup(BranchGroup scene) 
    {
        //BranchGroup objRoot = super.createSceneBranchGroup();
        BranchGroup objRoot =  m_SceneBranchGroup = new BranchGroup();
        // note that we are creating a TG *above* the TG
        // the is being controlled by the mouse behaviors.
        // The SUN mouse translate behavior would fail in this
        // instance as all movement would be in the X-Y plane
        // irrespective of any TG above the object.
        // The TornadoMouseTranslate behavior always moves an object
        // parrallel to the image plane
        
        TransformGroup objTrans1 = new TransformGroup();
        Transform3D t3d = new Transform3D();
        objTrans1.getTransform(t3d);
        t3d.setEuler(new Vector3d(0.9, 0.8, 0.3));
        objTrans1.setTransform(t3d);
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        
        // create the mouse scale behavior and set limits
        TornadoMouseScale mouseScale = new TornadoMouseScale(5, 0.1f);
        mouseScale.setMinScale(new Point3d(0.1, 0.1, 0.1));
        mouseScale.setMaxScale(new Point3d(10, 10, 10));
        mouseScale.setObject(objTrans);
        mouseScale.setChangeListener(this);
        mouseScale.setSchedulingBounds(getApplicationBounds());
        objTrans.addChild(mouseScale);
        // create the mouse rotate behavior
        TornadoMouseRotate mouseRotate = new TornadoMouseRotate(0.001, 0.001);
        mouseRotate.setInvert(true);
        mouseRotate.setObject(objTrans);
        mouseRotate.setChangeListener(this);
        mouseRotate.setSchedulingBounds(getApplicationBounds());
        objTrans.addChild(mouseRotate);
        // create the mouse translate behavior and set limits
        TornadoMouseTranslate mouseTrans = new TornadoMouseTranslate(0.005f);
        mouseTrans.setObject(objTrans);
        mouseTrans.setChangeListener(this);
        mouseTrans.setMinTranslate(new Point3d(-4, -4, -4));
        mouseTrans.setMaxTranslate(new Point3d(4, 4, 4));
        mouseTrans.setSchedulingBounds(getApplicationBounds());
        objTrans.addChild(mouseTrans);
        objTrans.addChild(new ColorCube(0.5));
        //objTrans.addChild(scene);
        
        // create some axis for the world to show it has been rotated
        ColorCube axis = new ColorCube(5.0);
        Appearance app = new Appearance();
        app.setPolygonAttributes(new PolygonAttributes( PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0));
        axis.setAppearance(app);
        objTrans1.addChild(axis);
        objTrans1.addChild(objTrans);
        objRoot.addChild(objTrans1);
        
        return objRoot;
    }
    protected Bounds getApplicationBounds() 
    {
        if (m_ApplicationBounds == null)
            m_ApplicationBounds = createApplicationBounds();
        return m_ApplicationBounds;
    }
    protected Bounds createApplicationBounds() 
    {
        m_ApplicationBounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        return m_ApplicationBounds;
    }
    public visualtra() {
    }

    public boolean ReadXML()
    {
        boolean bRet = false;
        //delaymsec = 1000;
        try 
        {  
            long CurMils = System.currentTimeMillis();
            //File fXmlFile = new File("SatCtrl/travisual.xml");  
            
            URL url = new URL(URLSOURCE+"/PostTra.aspx?"+CurMils);
            //URL url = new URL("http://24.84.57.253/SatCtrl/PostTra.aspx?"+CurMils);
            //URL url = new URL("http://24.84.57.253/SatCtrl/travisual.xml?"+CurMils);
            //URL url = new URL("http://localhost/SatCtrl/PostTra.aspx?"+CurMils);
            
            InputStream fXmlFile = url.openStream();
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();  
            
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();  
            Document doc = dBuilder.parse(fXmlFile);  
            doc.getDocumentElement().normalize();  
            //System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("Object");
            //            10000000.0
            double Coef = 10000000.0;
            //System.out.println("Information of all univerce");
            for (int s = 0; s < nodeLst.getLength(); s++) 
            {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element fstElmnt = (Element) fstNode;
                    NodeList fstTypeElmntLst = fstElmnt.getElementsByTagName("type");
                    Element fstTypeElmnt = (Element) fstTypeElmntLst.item(0);
                    NodeList fstType = fstTypeElmnt.getChildNodes();
                    String strType = ((Node) fstType.item(0)).getNodeValue();
                    
                    //System.out.println("Type : "  + strType);
                    NodeList lstXElmntLst = fstElmnt.getElementsByTagName("X");
                    Element lstXElmnt = (Element) lstXElmntLst.item(0);
                    NodeList lstX = lstXElmnt.getChildNodes();
                    String strX = ((Node) lstX.item(0)).getNodeValue();
                    //System.out.println("X: " + strX);
                    
                    NodeList lstYElmntLst = fstElmnt.getElementsByTagName("Z");
                    Element lstYElmnt = (Element) lstYElmntLst.item(0);
                    NodeList lstY = lstYElmnt.getChildNodes();
                    String strY = ((Node) lstY.item(0)).getNodeValue();
                   // System.out.println("Y: " + strY);                    
                    
                    NodeList lstZElmntLst = fstElmnt.getElementsByTagName("Y");
                    Element lstZElmnt = (Element) lstZElmntLst.item(0);
                    NodeList lstZ = lstZElmnt.getChildNodes();
                    String strZ = ((Node) lstZ.item(0)).getNodeValue();
                    //System.out.println("Z: " + strZ);                    
                    
                    NodeList lstRElmntLst = fstElmnt.getElementsByTagName("R");
                    Element lstRElmnt = (Element) lstRElmntLst.item(0);
                    NodeList lstR = lstRElmnt.getChildNodes();
                    String strR = ((Node) lstR.item(0)).getNodeValue();
                    //System.out.println("R: " + strZ);                    

                    NodeList lstRotElmntLst = fstElmnt.getElementsByTagName("Rot");
                    Element lstRotElmnt = (Element) lstRotElmntLst.item(0);
                    NodeList lstRot = lstRotElmnt.getChildNodes();
                    String strRot = ((Node) lstRot.item(0)).getNodeValue();
                    //System.out.println("R: " + strZ);                    
                    
                    if (strType.compareTo("Earth") == 0)
                    {
                        //EarthX = Double.valueOf(strX); EarthY= Double.valueOf(strY); EarthZ= -Double.valueOf(strZ);
                        EarthX = -Double.valueOf(strX); EarthY= Double.valueOf(strY); EarthZ= Double.valueOf(strZ);
                        EarthR= Double.valueOf(strR);
                        EarthRot= Double.valueOf(strRot);
                        EarthX /=Coef;EarthY /=Coef;EarthZ /=Coef;EarthR /=Coef;
                    }
                    
                    if (strType.compareTo("Moon") == 0)
                    {
                        //MoonX = Double.valueOf(strX); MoonY= Double.valueOf(strY); MoonZ= -Double.valueOf(strZ);
                        MoonX = -Double.valueOf(strX); MoonY= Double.valueOf(strY); MoonZ= Double.valueOf(strZ);
                        MoonR= Double.valueOf(strR);
                        MoonRot= Double.valueOf(strRot);
                        MoonX /=Coef;MoonY /=Coef;MoonZ /=Coef;MoonR /=Coef;
                    }
                    
                    if (strType.compareTo("Sun") == 0)
                    {
                        //SunX = Double.valueOf(strX); SunY= Double.valueOf(strY); SunZ= -Double.valueOf(strZ);
                        SunX = -Double.valueOf(strX); SunY= Double.valueOf(strY); SunZ= Double.valueOf(strZ);
                        SunR= Double.valueOf(strR);
                        SunX /=Coef;SunY /=Coef;SunZ /=Coef;SunR /=Coef;
                    }
                }
            }
            
            int iMaxGrst = 0;
            nodeLst = doc.getElementsByTagName("GrSt");
            for (int s = 0; s < nodeLst.getLength(); s++) 
            {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element fstElmnt = (Element) fstNode;
                    //NodeList fstTypeElmntLst = fstElmnt.getElementsByTagName("type");
                    //Element fstTypeElmnt = (Element) fstTypeElmntLst.item(0);
                    //NodeList fstType = fstTypeElmnt.getChildNodes();
                    //String strType = ((Node) fstType.item(0)).getNodeValue();
                    
                    //System.out.println("Type : "  + strType);
                    NodeList lstXElmntLst = fstElmnt.getElementsByTagName("X");//X
                    Element lstXElmnt = (Element) lstXElmntLst.item(0);
                    NodeList lstX = lstXElmnt.getChildNodes();
                    String strX = ((Node) lstX.item(0)).getNodeValue();
                    //System.out.println("X: " + strX);
                    
                    NodeList lstYElmntLst = fstElmnt.getElementsByTagName("Z");//Z
                    Element lstYElmnt = (Element) lstYElmntLst.item(0);
                    NodeList lstY = lstYElmnt.getChildNodes();
                    String strY = ((Node) lstY.item(0)).getNodeValue();
                   // System.out.println("Y: " + strY);                    
                    
                    NodeList lstZElmntLst = fstElmnt.getElementsByTagName("Y");//Y
                    Element lstZElmnt = (Element) lstZElmntLst.item(0);
                    NodeList lstZ = lstZElmnt.getChildNodes();
                    String strZ = ((Node) lstZ.item(0)).getNodeValue();
                    //System.out.println("Z: " + strZ);                    
                    
                    //GrStX[iMaxGrst] = Double.valueOf(strX); GrStY[iMaxGrst]= Double.valueOf(strY); GrStZ[iMaxGrst]= -Double.valueOf(strZ);
                    GrStX[iMaxGrst] = -Double.valueOf(strX); GrStY[iMaxGrst]= Double.valueOf(strY); GrStZ[iMaxGrst]= Double.valueOf(strZ);
                    GrStX[iMaxGrst] /=Coef;GrStY[iMaxGrst] /=Coef;GrStZ[iMaxGrst] /=Coef;
                    // 6 lines : 1 perpendicular + 4 parallel to earth surface and one pointing to Z
                    GrLines[iMaxGrst] = new LineArray(12,LineArray.COORDINATES); 
                    // first line perpendicular
                    GrLines[iMaxGrst].setCoordinate(0,new Point3f((float)GrStX[iMaxGrst], (float)GrStY[iMaxGrst], (float)GrStZ[iMaxGrst]));
                    GrLines[iMaxGrst].setCoordinate(1,new Point3f((float)(1.5*GrStX[iMaxGrst]), (float)(1.5*GrStY[iMaxGrst]), (float)(1.5*GrStZ[iMaxGrst])));
                    // second line to the polar start
                    GrLines[iMaxGrst].setCoordinate(2,new Point3f((float)GrStX[iMaxGrst], (float)GrStY[iMaxGrst], (float)GrStZ[iMaxGrst]));
                    if (GrStY[iMaxGrst]>0)
                        GrLines[iMaxGrst].setCoordinate(3,new Point3f((float)(GrStX[iMaxGrst]), (float)(GrStY[iMaxGrst]+0.5*EarthR), (float)(GrStZ[iMaxGrst])));
                    else
                        GrLines[iMaxGrst].setCoordinate(3,new Point3f((float)(GrStX[iMaxGrst]), (float)(GrStY[iMaxGrst]-0.5*EarthR), (float)(GrStZ[iMaxGrst])));
                    
                    double u1; double u2; double u3;
                    double v1; double v2; double v3;
                    double Xpr; double Ypr; double Zpr;
                    u1 = GrStX[iMaxGrst];u2 = GrStY[iMaxGrst];u3 = GrStZ[iMaxGrst];
                    v1 =0; v2 = EarthR ; v3 = 0;
                    Xpr = u2*v3 - u3*v2;
                    Ypr = u3*v1 - u1*v3;
                    Zpr = u1*v2-u2*v1;
                    double prMod = Math.sqrt(Xpr*Xpr + Ypr*Ypr +Zpr*Zpr);
                    Xpr/=prMod;Ypr/=prMod;Zpr/=prMod;
                    // parralel to surface
                    GrLines[iMaxGrst].setCoordinate(4,new Point3f((float)GrStX[iMaxGrst], (float)GrStY[iMaxGrst], (float)GrStZ[iMaxGrst]));
                    GrLines[iMaxGrst].setCoordinate(5,new Point3f((float)(GrStX[iMaxGrst]+Xpr*0.5*EarthR), (float)(GrStY[iMaxGrst]+Ypr*0.5*EarthR), (float)(GrStZ[iMaxGrst]+Zpr*0.5*EarthR)));
                    
                    GrLines[iMaxGrst].setCoordinate(6,new Point3f((float)GrStX[iMaxGrst], (float)GrStY[iMaxGrst], (float)GrStZ[iMaxGrst]));
                    GrLines[iMaxGrst].setCoordinate(7,new Point3f((float)(GrStX[iMaxGrst]-Xpr*0.5*EarthR), (float)(GrStY[iMaxGrst]-Ypr*0.5*EarthR), (float)(GrStZ[iMaxGrst]-Zpr*0.5*EarthR)));
                    v1 =Xpr*prMod; v2 = Ypr*prMod ; v3 = Zpr*prMod;
                    Xpr = u2*v3 - u3*v2;
                    Ypr = u3*v1 - u1*v3;
                    Zpr = u1*v2-u2*v1;
                    prMod = Math.sqrt(Xpr*Xpr + Ypr*Ypr +Zpr*Zpr);
                    Xpr/=prMod;Ypr/=prMod;Zpr/=prMod;
                    
                    GrLines[iMaxGrst].setCoordinate(8,new Point3f((float)GrStX[iMaxGrst], (float)GrStY[iMaxGrst], (float)GrStZ[iMaxGrst]));
                    GrLines[iMaxGrst].setCoordinate(9,new Point3f((float)(GrStX[iMaxGrst]+Xpr*0.5*EarthR), (float)(GrStY[iMaxGrst]+Ypr*0.5*EarthR), (float)(GrStZ[iMaxGrst]+Zpr*0.5*EarthR)));
                    
                    GrLines[iMaxGrst].setCoordinate(10,new Point3f((float)GrStX[iMaxGrst], (float)GrStY[iMaxGrst], (float)GrStZ[iMaxGrst]));
                    GrLines[iMaxGrst].setCoordinate(11,new Point3f((float)(GrStX[iMaxGrst]-Xpr*0.5*EarthR), (float)(GrStY[iMaxGrst]-Ypr*0.5*EarthR), (float)(GrStZ[iMaxGrst]-Zpr*0.5*EarthR)));

                    iMaxGrst++;
                }
            }
            iGrstMax = iMaxGrst;
            
            nodeLst = doc.getElementsByTagName("ObjectTime");
            for (int s = 0; s < nodeLst.getLength(); s++) 
            {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element fstElmnt = (Element) fstNode;
                    NodeList fstTypeElmntLst = fstElmnt.getElementsByTagName("timeJD");
                    Element fstTypeElmnt = (Element) fstTypeElmntLst.item(0);
                    NodeList fstType = fstTypeElmnt.getChildNodes();
                    String strTimeJD = ((Node) fstType.item(0)).getNodeValue();
                    TimeJD = Double.valueOf(strTimeJD);
                    
                    NodeList fstDElmntLst = fstElmnt.getElementsByTagName("timeYYDDMMHHMMSS");
                    Element fstDElmnt = (Element) fstDElmntLst.item(0);
                    NodeList fstD = fstDElmnt.getChildNodes();
                    TimeYYMMDDHHMMSS = ((Node) fstD.item(0)).getNodeValue();
                    
                    NodeList fstReloadElmntLst = fstElmnt.getElementsByTagName("ReloadInSec");
                    Element fstReloadElmnt = (Element) fstReloadElmntLst.item(0);
                    NodeList fstReload = fstReloadElmnt.getChildNodes();
                    delaymsec = Long.valueOf(((Node) fstReload.item(0)).getNodeValue());
                    delaymsec *= 1000;
                    
                    NodeList fstMNElmntLst = fstElmnt.getElementsByTagName("dMinFromNow");
                    Element fstMNElmnt = (Element) fstMNElmntLst.item(0);
                    NodeList fstMN = fstMNElmnt.getChildNodes();
                    dMinFromNow = Long.valueOf(((Node) fstMN.item(0)).getNodeValue());
                    
                }
            }
            
            double  XOld=0;
            double YOld=0;
            double ZOld=0;
            
            double XNext=0;
            double YNext=0;
            double ZNext=0;
            
            double XGRV=0;
            double YGRV=0;
            double ZGRV=0;

            
            int iMaxSat = 0;
            for (int iSat=0; iSat<10;iSat++)
            {
                int I2s = (int)dMinFromNow;
                String NameObject = "Sat"+iSat;
                NodeList nodeSatLst = doc.getElementsByTagName(NameObject);
                int iSizeOfElements = nodeSatLst.getLength();
                if (iSizeOfElements > 0)
                {
                    iMaxSat++;
                    switch(iSat)
                    {
                    case 0:Sat0Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;
                    case 1:Sat1Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;    
                    case 2:Sat2Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;
                    case 3:Sat3Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;    
                    case 4:Sat4Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;
                    case 5:Sat5Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;    
                    case 6:Sat6Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;
                    case 7:Sat7Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;    
                    case 8:Sat8Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;
                    case 9:Sat9Lines = new LineArray(2*(iSizeOfElements-1-I2s),LineArray.COORDINATES);break;    
                    }
                    XOld=0;
                    YOld=0;
                    ZOld=0;
            
                    XNext=0;
                    YNext=0;
                    ZNext=0;
                    int iDotsReq = I2s*2;
                    for (int s = 0; s < iSizeOfElements; s++) 
                    {
                        Node fstNode = nodeSatLst.item(s);
                        if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
                        {
                            Element fstElmnt = (Element) fstNode;
                            for (int ivGrst = 0; ivGrst <iGrstMax; ivGrst++)
                            {
                                String NameElemGrstX = "X"+ivGrst;
                                NodeList lstXGrElmntLst = fstElmnt.getElementsByTagName(NameElemGrstX);
                                Element lstXGrElmnt = (Element) lstXGrElmntLst.item(0);
                                if (lstXGrElmnt != null)
                                {
                                    iDotsReq+=2;
                                }
                            }
                        }
                    }
                    int count_to_antenna = I2s*2;
                    for (int s = 0; s < iSizeOfElements; s++) 
                    {
                        Node fstNode = nodeSatLst.item(s);
                        if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
                        {
                            Element fstElmnt = (Element) fstNode;
                            //NodeList fstTypeElmntLst = fstElmnt.getElementsByTagName("ID");
                            //Element fstTypeElmnt = (Element) fstTypeElmntLst.item(0);
                            //NodeList fstType = fstTypeElmnt.getChildNodes();
                            //String strType = ((Node) fstType.item(0)).getNodeValue(); // must be MoonTra
                    
                            NodeList lstXElmntLst = fstElmnt.getElementsByTagName("X");
                            Element lstXElmnt = (Element) lstXElmntLst.item(0);
                            NodeList lstX = lstXElmnt.getChildNodes();
                            String strX = ((Node) lstX.item(0)).getNodeValue();
                    
                            NodeList lstYElmntLst = fstElmnt.getElementsByTagName("Z");
                            Element lstYElmnt = (Element) lstYElmntLst.item(0);
                            NodeList lstY = lstYElmnt.getChildNodes();
                            String strY = ((Node) lstY.item(0)).getNodeValue();
                    
                            NodeList lstZElmntLst = fstElmnt.getElementsByTagName("Y");
                            Element lstZElmnt = (Element) lstZElmntLst.item(0);
                            NodeList lstZ = lstZElmnt.getChildNodes();
                            String strZ = ((Node) lstZ.item(0)).getNodeValue();
                            
                            if (XOld == 0)
                            {
                                //XOld = Double.valueOf(strX); YOld= Double.valueOf(strY); ZOld= -Double.valueOf(strZ);
                                XOld = -Double.valueOf(strX); YOld= Double.valueOf(strY); ZOld= Double.valueOf(strZ);
                                XOld /=Coef;YOld /=Coef;ZOld /=Coef;
                                // alloocate enought for last 3 min (I2s var) and lines to ground satations
                                switch(iSat)
                                {
                                case 0:Sat0Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;
                                case 1:Sat1Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;    
                                case 2:Sat2Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;
                                case 3:Sat3Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;    
                                case 4:Sat4Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;
                                case 5:Sat5Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;    
                                case 6:Sat6Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;
                                case 7:Sat7Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;    
                                case 8:Sat8Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;
                                case 9:Sat9Dot = new LineArray(iDotsReq,LineArray.COORDINATES);break;    
                                }
                            }
                            else
                            {
                                //XNext = Double.valueOf(strX); YNext= Double.valueOf(strY); ZNext= -Double.valueOf(strZ);
                                XNext = -Double.valueOf(strX); YNext= Double.valueOf(strY); ZNext= Double.valueOf(strZ);
                                XNext /=Coef;YNext /=Coef;ZNext /=Coef;
                                if (s <= (iSizeOfElements-1-I2s))
                                {
                                    switch(iSat)
                                    {
                                    case 0:
                                        Sat0Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat0Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 1:
                                        Sat1Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat1Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 2:
                                        Sat2Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat2Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 3:
                                        Sat3Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat3Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 4:
                                        Sat4Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat4Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 5:
                                        Sat5Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat5Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 6:
                                        Sat6Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat6Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 7:
                                        Sat7Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat7Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 8:
                                        Sat8Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat8Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 9:
                                        Sat9Lines.setCoordinate((s-1)*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat9Lines.setCoordinate((s-1)*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    }
                                }
                                else
                                {
                                    switch(iSat)
                                    {
                                    case 0:
                                        Sat0Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat0Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 1:
                                        Sat1Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat1Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 2:
                                        Sat2Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat2Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 3:
                                        Sat3Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat3Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 4:
                                        Sat4Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat4Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 5:
                                        Sat5Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat5Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 6:
                                        Sat6Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat6Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 7:
                                        Sat7Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat7Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 8:
                                        Sat8Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat8Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    case 9:
                                        Sat9Dot.setCoordinate((s-(iSizeOfElements-I2s))*2,new Point3f((float)XOld, (float)YOld, (float)ZOld));
                                        Sat9Dot.setCoordinate((s-(iSizeOfElements-I2s))*2+1,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                        break;
                                    }
                                    
                                }
                                    if ((iDotsReq != I2s*2))// && (s == (iSizeOfElements-1)))
                                    {
                                        
                                        for (int ivGrst = 0; ivGrst <iGrstMax; ivGrst++)
                                        {
                                            String NameElemXGRV = "X"+ivGrst;
                                            String NameElemYGRV = "Y"+ivGrst;
                                            String NameElemZGRV = "Z"+ivGrst;
                                            NodeList lstXGRVElmntLst = fstElmnt.getElementsByTagName(NameElemXGRV);
                                            Element lstXGRVElmnt = (Element) lstXGRVElmntLst.item(0);
                                            if (lstXGRVElmnt != null)
                                            {
                                                //Element lstXGRVElmnt = (Element) lstXGRVElmntLst.item(0);
                                                NodeList lstXGRV = lstXGRVElmnt.getChildNodes();
                                                String strXGRV = ((Node) lstXGRV.item(0)).getNodeValue();
                    
                                                NodeList lstYGRVElmntLst = fstElmnt.getElementsByTagName(NameElemZGRV);
                                                Element lstYGRVElmnt = (Element) lstYGRVElmntLst.item(0);
                                                NodeList lstYGRV = lstYGRVElmnt.getChildNodes();
                                                String strYGRV = ((Node) lstYGRV.item(0)).getNodeValue();
                    
                                                NodeList lstZGRVElmntLst = fstElmnt.getElementsByTagName(NameElemYGRV);
                                                Element lstZGRVElmnt = (Element) lstZGRVElmntLst.item(0);
                                                NodeList lstZGRV = lstZGRVElmnt.getChildNodes();
                                                String strZGRV = ((Node) lstZGRV.item(0)).getNodeValue();
                                                
                                                XGRV = -Double.valueOf(strXGRV); YGRV= Double.valueOf(strYGRV); ZGRV= Double.valueOf(strZGRV);
                                                XGRV /=Coef;YGRV /=Coef;ZGRV /=Coef;
                                                switch(iSat)
                                                {
                                                case 0:
                                                    Sat0Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat0Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 1:
                                                    Sat1Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat1Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 2:
                                                    Sat1Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat2Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 3:
                                                    Sat3Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat3Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 4:
                                                    Sat4Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat4Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 5:
                                                    Sat5Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat5Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 6:
                                                    Sat6Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat6Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 7:
                                                    Sat7Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat7Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 8:
                                                    Sat8Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat8Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                case 9:
                                                    Sat9Dot.setCoordinate(count_to_antenna++,new Point3f((float)XGRV, (float)YGRV, (float)ZGRV));
                                                    Sat9Dot.setCoordinate(count_to_antenna++,new Point3f((float)XNext, (float)YNext, (float)ZNext));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                XOld = XNext; YOld = YNext; ZOld = ZNext;
                                bRet = true;
                            }
                        }
                    }
                }
            }
            MaxSat = iMaxSat;
            

        } 
        catch (Exception e) 
        {  
            e.printStackTrace();  
            delaymsec += 1000;
        }
        return bRet;
    }
    public void init() 
    {
        String mURLSOURCE = this.getParameter("URLSOURCE");
        String GetStarmap = this.getParameter("STARMAP");
        if (mURLSOURCE != null)
            URLSOURCE = mURLSOURCE;
        while(ReadXML()==false)
        {
            
        }
        CenterX=EarthX;CenterY=EarthY;CenterZ=EarthZ;
        try 
        {
            //TextureLoader texLoader =  new TextureLoader( "SatCtrl/Earth-Color_960_Koord.jpg", this);
            //URL ur = new URL("http://192.168.0.102/SatCtrl/Earth-Color_960_Koord.jpg");
            //URL ur = new URL(URLSOURCE+"/earthsatellitemap.jpg");
            //URL ur = new URL("http://24.84.57.253/SatCtrl/Earth-Color_960_Koord.jpg");
            //URL ur = new URL("http://localhost/SatCtrl/Earth-Color_960_Koord.jpg");
            //URL ur = new URL("http://localhost/SatCtrl/earthsatellitemap.jpg");
            //TextureLoader texLoader =  new TextureLoader( ur, this);
            
            TextureLoader texLoader =  new TextureLoader( this.getClass().getResource("earthsatellitemap.jpg"), this);
            texEarth = texLoader.getTexture();
        }
        catch (Exception e) 
        {  
            e.printStackTrace();  
        }
            
        try 
        {
            //TextureLoader texLoader =  new TextureLoader( "SatCtrl/moon___map_by_horizoied-d3y3lvg.jpg", this);
            //URL ur = new URL("http://192.168.0.102/SatCtrl/moon___map_by_horizoied-d3y3lvg.jpg");
            //URL ur = new URL(URLSOURCE+"/moon___map_by_horizoied-d3y3lvg.jpg");
            //URL ur = new URL("http://localhost/SatCtrl/moon___map_by_horizoied-d3y3lvg.jpg");
            //TextureLoader texLoader =  new TextureLoader( ur, this);
            TextureLoader texLoader =  new TextureLoader( this.getClass().getResource("moon___map_by_horizoied-d3y3lvg.jpg"), this);
            texMoon = texLoader.getTexture();
        
            //URL myURl = URL("http://192.168.0.102/SatCtrl/Map_Earth_2100_by_JamesVF.jpg");
        } 
        catch (Exception e) 
        {  
            e.printStackTrace();  
        }
        
        try 
        {
            //TextureLoader texLoader =  new TextureLoader( "SatCtrl/SkyMap2.jpg", this);
            //URL ur = new URL("http://192.168.0.102/SatCtrl/SkyMap2.jpg");
            //URL ur = new URL(URLSOURCE+"/SkyMap2.jpg");
            //URL ur = new URL("http://localhost/SatCtrl/SkyMap2.jpg");
            //TextureLoader texLoader =  new TextureLoader( ur, this);
            if (GetStarmap != null)
            {
                TextureLoader texLoader =  new TextureLoader( this.getClass().getResource("SkyMap2.jpg"), this);
                texSky = texLoader.getTexture();
                ImageSkyMap = texLoader.getImage();
            }
        } 
        catch (Exception e) 
        {  
            e.printStackTrace();  
        }
        

	//setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(config);

        // create preview window:
        //c.setSize(200, 200);
	//add("Center", c);
	u = new SimpleUniverse(c);
        Color3f lColor2   = new Color3f(0.0f, 0.0f, 1.0f);
	Color3f bgColor   = new Color3f(0.05f, 0.05f, 0.2f);
	// Create the root of the branch graph
	//BranchGroup objRoot = new BranchGroup();
        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        //TransformGroup objScale = new TransformGroup();
        //Transform3D t3dI = new Transform3D();
        ////Vector3f vector = new Vector3f(-.2f,.1f , -.4f);
        //t3dI.setScale(0.8);
        ////t3dI.setTranslation(vector);
        //objScale.setTransform(t3dI);
        //objRoot.addChild(objScale);

	// Create a bounds for the background and lights
	BoundingSphere bounds =  new BoundingSphere(new Point3d(0.0,0.0,0.0), 10000.0);
        
        // Set up the background
	Background bg = new Background(bgColor);
                
        //if (ImageSkyMap != null)
        //    bg.setImage(ImageSkyMap);
        //bg.setImageScaleMode(Background.SCALE_FIT_ALL);
	bg.setApplicationBounds(bounds);
	
        
        BranchGroup backGeoBranch = new BranchGroup();
        Sphere sphereObj = new Sphere(8.0f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_NORMALS_INWARD | Sphere.GENERATE_TEXTURE_COORDS, 100);
        Appearance backgroundApp = sphereObj.getAppearance();
        backgroundApp.setTexture(texSky);
        //backGeoBranch.addChild(sphereObj);
       
        bg.setGeometry(backGeoBranch);

        BranchGroup scene = new BranchGroup();//objRoot;
        
        
        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        //u.getViewingPlatform().setNominalViewingTransform();
        
        u.addBranchGraph(scene);

        //BoundingSphere bounds =  new BoundingSphere(new Point3d(0.0,0.0,0.0), 10000.0);
        
        Locale locale = new Locale(u);
        m_SceneBranchGroup = new BranchGroup();
        // note that we are creating a TG *above* the TG
        // the is being controlled by the mouse behaviors.
        // The SUN mouse translate behavior would fail in this
        // instance as all movement would be in the X-Y plane
        // irrespective of any TG above the object.
        // The TornadoMouseTranslate behavior always moves an object
        // parrallel to the image plane
        
        TransformGroup objTrans1 = new TransformGroup();
        Transform3D t3d = new Transform3D();
        objTrans1.getTransform(t3d);
        ////////////////////////////////////////////////////////////////////////////////////////
        // that is initial rotation
        ////////////////////////////////////////////////////////////////////////////////////////
        //t3d.setEuler(new Vector3d(0.9, 0.8, 0.3));
        t3d.setEuler(new Vector3d(0.0, 0.0, 0.0));
        objTrans1.setTransform(t3d);
        
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objTrans.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        objTrans.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_PICKABLE_READ);
        objTrans.setCapability(TransformGroup.ALLOW_PICKABLE_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        
        
        m_Earth_Moon = objTrans;
        
        m_Transform3D = new Transform3D();
        m_TransformY = new Transform3D();
        
        m_Transform3DMoon= new Transform3D();
        m_Transform3DSun= new Transform3D();
        
        // create the mouse scale behavior and set limits
        TornadoMouseScale mouseScale = new TornadoMouseScale(5, 0.05f);
        mouseScale.setMinScale(new Point3d(0.1, 0.1, 0.1));
        mouseScale.setMaxScale(new Point3d(20, 20, 20));
        mouseScale.setObject(objTrans);
        mouseScale.setChangeListener(this);
        mouseScale.setSchedulingBounds(getApplicationBounds());
        objTrans.addChild(mouseScale);
        // create the mouse rotate behavior
        TornadoMouseRotate mouseRotate = new TornadoMouseRotate(0.001, 0.001);
        mouseRotate.setInvert(true);
        mouseRotate.setObject(objTrans);
        mouseRotate.setChangeListener(this);
        mouseRotate.setSchedulingBounds(getApplicationBounds());
        objTrans.addChild(mouseRotate);
        // create the mouse translate behavior and set limits
        TornadoMouseTranslate mouseTrans = new TornadoMouseTranslate(0.0004f);
        mouseTrans.setObject(objTrans);
        mouseTrans.setChangeListener(this);
        mouseTrans.setMinTranslate(new Point3d(-10, -10, -10));
        mouseTrans.setMaxTranslate(new Point3d(20, 20, 20));
        mouseTrans.setSchedulingBounds(getApplicationBounds());
        objTrans.addChild(mouseTrans);
        //objTrans.addChild(new ColorCube(0.5));
        //objTrans.addChild(scene);
        
                
        //objTrans.addChild(sphereObj);
        objTrans.addChild(bg);
       
       	// Create a Earth Sphere object, generate one copy of the sphere,
	// and add it into the scene graph.
        Color3f eColorEarth    = new Color3f(.0f, .0f, .0f);
	Color3f sColorEarth    = new Color3f(1.0f, 1.0f, 1.0f);
	Color3f objColorEarth  = new Color3f(.90f, .90f, .90f);
	Material m = new Material(objColorEarth, eColorEarth, objColorEarth, sColorEarth, 100.0f);
        //Material m = new Material();
	Appearance a = new Appearance();
	m.setLightingEnable(true);
	a.setMaterial(m);
        a.setTexture(texEarth);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.COMBINE);//.COMBINE);//.BLEND);//.MODULATE);
        a.setTextureAttributes(texAttr);
	Sphere sphEarth = new Sphere((float)EarthR, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 280, a);//.GENERATE_NORMALS, 180, a);
        
        // position of the earth
        Transform3D LocationEarth = new Transform3D();
        Vector3f vectorEarth = new Vector3f((float)(EarthX-CenterX), (float)(EarthY-CenterY) , (float)(EarthZ-CenterZ));
        LocationEarth.setTranslation(vectorEarth);
        TransformGroup tg = new TransformGroup(LocationEarth);
        tg.addChild(sphEarth);
        // add ground stations
        Appearance aGrDot = new Appearance();
        ColoringAttributes GrDotColor = new ColoringAttributes();
        GrDotColor.setColor(new Color3f(2.0f,2.0f,0f));
        aGrDot.setColoringAttributes(GrDotColor);
        for (int iGr=0; iGr <iGrstMax; iGr++)
        {
            Shape3D GrDot = new Shape3D(GrLines[iGr]); GrDot.setAppearance(aGrDot);tg.addChild(GrDot);
        }
        // then spin the earth
        
            Transform3D m_EarthRotationDelta = new Transform3D();
            m_EarthRotation = new Transform3D();
            //double y_angle =  (EarthRot+90) * 3.14159265358979323846264338327950288/ 180;
            //double y_angle =  (EarthRot-90) * 3.14159265358979323846264338327950288/ 180;
            double y_angle =  (EarthRot) * 3.14159265358979323846264338327950288/ 180;
            EarthRotOld = EarthRot;
            m_EarthRotationDelta.rotY(y_angle);
            Matrix4d mat = new Matrix4d();
            m_EarthRotation.get(mat);
            m_EarthRotation.mul(m_EarthRotation, m_EarthRotationDelta);
            Vector3d translation = new Vector3d(mat.m03, mat.m13, mat.m23);
            m_EarthRotation.setTranslation(translation);

            m_TransformGrEarthRotation = new TransformGroup(m_EarthRotation);
            m_TransformGrEarthRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            m_TransformGrEarthRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            m_TransformGrEarthRotation.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
            m_TransformGrEarthRotation.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
            m_TransformGrEarthRotation.setCapability(TransformGroup.ALLOW_PICKABLE_READ);
            m_TransformGrEarthRotation.setCapability(TransformGroup.ALLOW_PICKABLE_WRITE);
            m_TransformGrEarthRotation.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

            m_TransformGrEarthRotation.addChild(tg);
            m_TransformGrEarthRotation.setTransform(m_EarthRotation);
            objTrans.addChild(m_TransformGrEarthRotation);
            
        //objTrans.addChild(tg);

        // Create a Moon Sphere object, generate one copy of the sphere,
	// and add it into the scene graph.
        Color3f eColorMoon    = new Color3f(.0f, .0f, .0f);
	Color3f sColorMoon    = new Color3f(1.0f, 1.0f, 1.0f);
	Color3f objColorMoon  = new Color3f(.90f, .90f, .90f);
	Material mMoon = new Material(objColorMoon, eColorMoon, objColorMoon, sColorMoon, 100.0f);
	Appearance aMoon = new Appearance();
	//aMoon.setLightingEnable(true);
	aMoon.setMaterial(mMoon);
        aMoon.setTexture(texMoon);
        TextureAttributes texAttrMoon = new TextureAttributes();
        texAttrMoon.setTextureMode(TextureAttributes.COMBINE);//.COMBINE);//.BLEND);//.MODULATE);
        aMoon.setTextureAttributes(texAttrMoon);
	Sphere sphMoon = new Sphere((float)MoonR, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 180, aMoon);//.GENERATE_NORMALS, 180, a);

            Transform3D m_MoonRotationDelta = new Transform3D();
            m_MoonRotation = new Transform3D();
            double y_anglem =  MoonRot * 3.14159265358979323846264338327950288/ 180;
            MoonRotOld = MoonRot;
            m_MoonRotationDelta.rotY(-y_anglem);
            Matrix4d matM = new Matrix4d();
            m_MoonRotation.get(matM);
            m_MoonRotation.mul(m_MoonRotation, m_MoonRotationDelta);
            Vector3d translationM = new Vector3d(matM.m03, matM.m13, matM.m23);
            m_MoonRotation.setTranslation(translationM);

            m_TransformGrMoonRotation = new TransformGroup(m_MoonRotation);
            m_TransformGrMoonRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            m_TransformGrMoonRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            m_TransformGrMoonRotation.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
            m_TransformGrMoonRotation.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
            m_TransformGrMoonRotation.setCapability(TransformGroup.ALLOW_PICKABLE_READ);
            m_TransformGrMoonRotation.setCapability(TransformGroup.ALLOW_PICKABLE_WRITE);
            m_TransformGrMoonRotation.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

            m_TransformGrMoonRotation.addChild(sphMoon);
            m_TransformGrMoonRotation.setTransform(m_MoonRotation);
            //objTrans.addChild(m_TransformGrEarthRotation);

        
        
        Transform3D LocationMoon = new Transform3D();
        Vector3f vectorMoon = new Vector3f((float)(MoonX-CenterX), (float)(MoonY-CenterY), (float)(MoonZ-CenterZ));
        LocationMoon.setTranslation(vectorMoon);
        tgMoon = new TransformGroup(LocationMoon);
        tgMoon.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgMoon.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tgMoon.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        tgMoon.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        tgMoon.setCapability(TransformGroup.ALLOW_PICKABLE_READ);
        tgMoon.setCapability(TransformGroup.ALLOW_PICKABLE_WRITE);
        tgMoon.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        tgMoon.addChild(m_TransformGrMoonRotation);
        //tgMoon.addChild(sphMoon);
        objTrans.addChild(tgMoon);
        
        //MoonTra = new BranchGroup();  
        //MoonTra.setCapability(BranchGroup.ALLOW_DETACH);  
        //MoonTra.addChild(new Shape3D(MoonLines));
        //objTrans.addChild(MoonTra);
        Appearance aSatDot = new Appearance();
        ColoringAttributes SatDotColor = new ColoringAttributes();
        SatDotColor.setColor(new Color3f(2.0f,0f,0f));
        aSatDot.setColoringAttributes(SatDotColor);
        for (int iSat=0; iSat<MaxSat;iSat++)
        {
            Shape3D SatDot = null;        
            switch(iSat)
            {
            case 0: //objTrans.addChild(new Shape3D(Sat0Lines)); SatDot = new Shape3D(Sat0Dot); 
            SatTra0= new BranchGroup();SatTra0.setCapability(BranchGroup.ALLOW_DETACH);  SatTra0.addChild(new Shape3D(Sat0Lines));SatDot = new Shape3D(Sat0Dot); SatDot.setAppearance(aSatDot);SatTra0.addChild(SatDot); objTrans.addChild(SatTra0);break;
            case 1:SatTra1= new BranchGroup();SatTra1.setCapability(BranchGroup.ALLOW_DETACH);  SatTra1.addChild(new Shape3D(Sat1Lines));SatDot = new Shape3D(Sat1Dot); SatDot.setAppearance(aSatDot);SatTra1.addChild(SatDot); objTrans.addChild(SatTra1);break;
            case 2:SatTra2= new BranchGroup();SatTra2.setCapability(BranchGroup.ALLOW_DETACH);  SatTra2.addChild(new Shape3D(Sat2Lines));SatDot = new Shape3D(Sat2Dot); SatDot.setAppearance(aSatDot);SatTra2.addChild(SatDot); objTrans.addChild(SatTra2);break;
            case 3:SatTra3= new BranchGroup();SatTra3.setCapability(BranchGroup.ALLOW_DETACH);  SatTra3.addChild(new Shape3D(Sat3Lines));SatDot = new Shape3D(Sat3Dot); SatDot.setAppearance(aSatDot);SatTra3.addChild(SatDot); objTrans.addChild(SatTra3);break;
            case 4:SatTra4= new BranchGroup();SatTra4.setCapability(BranchGroup.ALLOW_DETACH);  SatTra4.addChild(new Shape3D(Sat4Lines));SatDot = new Shape3D(Sat4Dot); SatDot.setAppearance(aSatDot);SatTra4.addChild(SatDot); objTrans.addChild(SatTra4);break;
            case 5:SatTra5= new BranchGroup();SatTra5.setCapability(BranchGroup.ALLOW_DETACH);  SatTra5.addChild(new Shape3D(Sat5Lines));SatDot = new Shape3D(Sat5Dot); SatDot.setAppearance(aSatDot);SatTra5.addChild(SatDot); objTrans.addChild(SatTra5);break;
            case 6:SatTra6= new BranchGroup();SatTra6.setCapability(BranchGroup.ALLOW_DETACH);  SatTra6.addChild(new Shape3D(Sat6Lines));SatDot = new Shape3D(Sat6Dot); SatDot.setAppearance(aSatDot);SatTra6.addChild(SatDot); objTrans.addChild(SatTra6);break;
            case 7:SatTra7= new BranchGroup();SatTra7.setCapability(BranchGroup.ALLOW_DETACH);  SatTra7.addChild(new Shape3D(Sat7Lines));SatDot = new Shape3D(Sat7Dot); SatDot.setAppearance(aSatDot);SatTra7.addChild(SatDot); objTrans.addChild(SatTra7);break;
            case 8:SatTra8= new BranchGroup();SatTra8.setCapability(BranchGroup.ALLOW_DETACH);  SatTra8.addChild(new Shape3D(Sat8Lines));SatDot = new Shape3D(Sat8Dot); SatDot.setAppearance(aSatDot);SatTra8.addChild(SatDot); objTrans.addChild(SatTra8);break;
            case 9:SatTra9= new BranchGroup();SatTra9.setCapability(BranchGroup.ALLOW_DETACH);  SatTra9.addChild(new Shape3D(Sat9Lines));SatDot = new Shape3D(Sat9Dot); SatDot.setAppearance(aSatDot);SatTra9.addChild(SatDot); objTrans.addChild(SatTra9);break;
            }
            //SatDot.setAppearance(aSatDot); //objTrans.addChild(SatDot);
            //SatTra.addChild(SatDot);
        }
        
        
        // Create a Sun Sphere object, generate one copy of the sphere,
	// and add it into the scene graph.
        Color3f eColorSun    = new Color3f(2.0f, 2.0f, 1.0f);
	Color3f sColorSun    = new Color3f(0.0f, 0.0f, 0.0f);
	Color3f objColorSun  = new Color3f(.0f, .0f, .0f);
	m = new Material(objColorSun, eColorSun, objColorSun, sColorSun, 100.0f);
        //Material m = new Material();
	a = new Appearance();
	m.setLightingEnable(true);
	a.setMaterial(m);
        //a.setTexture(texEarth);
        //texAttr.setTextureMode(TextureAttributes.COMBINE);//.COMBINE);//.BLEND);//.MODULATE);
        //a.setTextureAttributes(texAttr);
	Sphere sphSun = new Sphere((float)SunR/100, Sphere.GENERATE_NORMALS , 180, a);//.GENERATE_NORMALS, 180, a);

        Transform3D LocationSunLt = new Transform3D();
        tgLtSun = new TransformGroup(LocationSunLt);
        tgLtSun.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgLtSun.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tgLtSun.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        tgLtSun.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        tgLtSun.setCapability(TransformGroup.ALLOW_PICKABLE_READ);
        tgLtSun.setCapability(TransformGroup.ALLOW_PICKABLE_WRITE);
        tgLtSun.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        
        // position of the sun (kind of)
        Transform3D LocationSun = new Transform3D();
        Vector3f vectorSun = new Vector3f((float)(SunX-CenterX)/100, (float)(SunY-CenterY)/100 , (float)(SunZ-CenterZ)/100);
        LocationSun.setTranslation(vectorSun);
        TransformGroup tgSun = new TransformGroup(LocationSun);
        tgSun.addChild(sphSun);
        //objTrans.addChild(tgSun);
        
        Point3f atten = new Point3f(.010f, .0f, .0f);
        Color3f lColor1   = new Color3f(10.10f, 10.10f, 10.10f);
        Point3f lSunPoint  = new Point3f((float)(SunX-CenterX), (float)(SunY-CenterY), (float)(SunZ-CenterZ));
        Light lgtSun = null;
        Vector3d lPos1 =  new Vector3d((float)(SunX-CenterX), (float)(SunY-CenterY), (float)(SunZ-CenterZ));
        Vector3f lDirect1 = new Vector3f(lPos1);
	lDirect1.negate();
        lgtSun = new DirectionalLight(lColor1, lDirect1);
        //lgtSun = new PointLight(lColor1, lSunPoint, atten);
        lgtSun.setInfluencingBounds(bounds);
	//objTrans.addChild(lgtSun);
        tgLtSun.addChild(lgtSun);
        
        tgLtSun.addChild(tgSun);
        objTrans.addChild(tgLtSun);
        
	TransformGroup l1RotTrans = new TransformGroup();
	l1RotTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objTrans.addChild(l1RotTrans);

	TransformGroup l2RotTrans = new TransformGroup();
	l2RotTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objTrans.addChild(l2RotTrans);

        Color3f alColor   = new Color3f(.2f, .2f, .2f);
        AmbientLight aLgt = new AmbientLight(alColor);
	

        
	// Create transformations for the positional lights
	//Transform3D t = new Transform3D();
	//Vector3d lPos1 =  new Vector3d(0.0, 0.0, 2.0);
	//t.set(lPos1);
	//TransformGroup l1Trans = new TransformGroup(t);
	//l1RotTrans.addChild(l1Trans);
        // Create Geometry for point lights
        
        //ColoringAttributes caL1 = new ColoringAttributes();
        //caL1.setColor(lColor1);
        //Appearance appL1 = new Appearance();
        //appL1.setColoringAttributes(caL1);
        //l1Trans.addChild(new Sphere(0.1f, appL1));
        // Create lights
    
	//Vector3f lDirect1 = new Vector3f(lPos1);
	//lDirect1.negate();

        // Create transformations for the positional lights
	//t = new Transform3D();
	//Vector3d lPos2 = new Vector3d(0.5, 0.8, 2.0);
	//t.set(lPos2);
	//TransformGroup l2Trans = new TransformGroup(t);
	//l2RotTrans.addChild(l2Trans);
        // Create Geometry for point lights
	//Color3f lColor2   = new Color3f(0.0f, 0.0f, 1.0f);
        
        //ColoringAttributes caL2 = new ColoringAttributes();
	//caL2.setColor(lColor2);
	//Appearance appL2 = new Appearance();
	//appL2.setColoringAttributes(caL2);
	//l2Trans.addChild(new Sphere(0.01f, appL2));
	// Create lights
	//Light lgt2 = null;
	//Vector3f lDirect2 = new Vector3f(lPos2);
	//lDirect2.negate();

        

	// Set the influencing bounds
	aLgt.setInfluencingBounds(bounds);
        
	
	//lgt2.setInfluencingBounds(bounds);

	// Add the lights into the scene graph
        //tgLtSun.addChild(aLgt);
        //tgLtSun.addChild(lgtSun);
        //objTrans.addChild(tgLtSun);
	objTrans.addChild(aLgt);

        
	//l2Trans.addChild(lgt2);
        
        objTrans1.addChild(objTrans);
        m_SceneBranchGroup.addChild(objTrans1);
        
        ViewPlatform vp = new ViewPlatform();
        vp.setViewAttachPolicy(View.CYCLOPEAN_EYE_VIEW);//.RELATIVE_TO_FIELD_OF_VIEW);
        vp.setActivationRadius(getViewPlatformActivationRadius());
        
        BranchGroup viewBranchGroup = createViewBranchGroup(getViewTransformGroupArray(), vp);
        
        View view = new View();
        PhysicalBody pb = new PhysicalBody();
        PhysicalEnvironment pe = new PhysicalEnvironment();
       
        view.setPhysicalEnvironment(pe);
        view.setPhysicalBody(pb);
        if (vp != null)
            view.attachViewPlatform(vp);
        view.setBackClipDistance(getBackClipDistance());
        view.setFrontClipDistance(getFrontClipDistance());
        Canvas3D c3d = createCanvas3D();
        view.addCanvas3D(c3d);
        addCanvas3D(c3d);
        
        Background background = null;//createBackground();
        if (background != null)
            m_SceneBranchGroup.addChild(background);
        // m_Java3dTree.recursiveApplyCapability(sceneBranchGroup);
        //m_Java3dTree.recursiveApplyCapability(viewBranchGroup);
        locale.addBranchGraph(m_SceneBranchGroup);
        addViewBranchGroup(locale, viewBranchGroup);
        if(XMLTimerread == null)
        {
             XMLTimerread = new Thread(this);
             XMLTimerread.start();
        }
    }
    public void run()
    {
         while(true)
         {
             repaint();
             try
             {
                 XMLTimerread.sleep(delaymsec);
                 System.gc();
                 TimerCount++;
                 if (ReadXML())
                 {
                    CenterX=EarthX;CenterY=EarthY;CenterZ=EarthZ;
                    TransformGroup tg = getTransformEarthMoon();
                    if (tg != null) 
                    {        
                        Enumeration AllCh = tg.getAllChildren();
                        int nIndex = 0;
                        Object obj = null;
                        //scan through the child nodes until we find the one that 
                        //corresponds to our data structure.
                        /*
                        while( AllCh.hasMoreElements() != false )
                        {
                            obj = (Object) AllCh.nextElement();
                            if (obj == MoonTra)
                                break;
                            nIndex++;
                        }
                        if (obj != null)
                        {
                            tg.removeChild(nIndex);
                            MoonTra = new BranchGroup();  
                            MoonTra.setCapability(BranchGroup.ALLOW_DETACH);  
                            MoonTra.addChild(new Shape3D(MoonLines));
                            ((TransformGroup)m_Earth_Moon).addChild(MoonTra);
                        }*/
                        
                        
                        while( AllCh.hasMoreElements() != false )
                        {
                            obj = (Object) AllCh.nextElement();
                            if (obj == tgMoon)
                            {
                                Vector3d vTranslation = new Vector3d((float)(MoonX-CenterX), (float)(MoonY-CenterY), (float)(MoonZ-CenterZ));
                                m_Transform3DMoon.setTranslation(vTranslation);
                                ((TransformGroup)obj).setTransform(m_Transform3DMoon);
                                //break;
                            }
                            if (obj == tgLtSun)
                            {
                                Vector3d vTranslation = new Vector3d((float)(SunX-CenterX)/100, (float)(SunY-CenterY)/100, (float)(SunZ-CenterZ)/100);
                                m_Transform3DSun.setTranslation(vTranslation);
                                ((TransformGroup)obj).setTransform(m_Transform3DSun);
                            }
                            if (obj == m_TransformGrEarthRotation)
                            {
                                Transform3D m_EarthRotationDelta = new Transform3D();
                                Transform3D  m_EarthRotation = new Transform3D();
                                //double y_angle =  (EarthRot-EarthRotOld) * 3.14159265358979323846264338327950288/ 180;
                                //double y_angle =  (EarthRot+90) * 3.14159265358979323846264338327950288/ 180;
                                //double y_angle =  (EarthRot-90) * 3.14159265358979323846264338327950288/ 180;
                                double y_angle =  (EarthRot) * 3.14159265358979323846264338327950288/ 180;
                                EarthRotOld = EarthRot;
                                m_EarthRotationDelta.rotY(y_angle);
                                Matrix4d mat = new Matrix4d();
                                m_EarthRotation.get(mat);
                                m_EarthRotation.mul(m_EarthRotation, m_EarthRotationDelta);
                                Vector3d translation = new Vector3d(mat.m03, mat.m13, mat.m23);
                                m_EarthRotation.setTranslation(translation);

                                ((TransformGroup)obj).setTransform(m_EarthRotation);
                                
                            }
                            nIndex++;
                        }
                        Appearance aSatDot = new Appearance();
                        ColoringAttributes SatDotColor = new ColoringAttributes();
                        SatDotColor.setColor(new Color3f(2.0f,0f,0f));
                        aSatDot.setColoringAttributes(SatDotColor);
                        Shape3D SatDot = null;
                        for (int iSat=0; iSat<MaxSat;iSat++)
                        {
                            tg = getTransformEarthMoon();
                            AllCh = tg.getAllChildren();
                            nIndex = 0;
                            obj = null;
                            boolean iFound;
                        
                            while( AllCh.hasMoreElements() != false )
                            {
                                obj = (Object) AllCh.nextElement();
                                iFound = false;
                                switch(iSat)
                                {
                                case 0:if (obj == SatTra0) iFound = true;break;
                                case 1:if (obj == SatTra1) iFound = true;break;
                                case 2:if (obj == SatTra2) iFound = true;break;
                                case 3:if (obj == SatTra3) iFound = true;break;
                                case 4:if (obj == SatTra4) iFound = true;break;
                                case 5:if (obj == SatTra5) iFound = true;break;
                                case 6:if (obj == SatTra6) iFound = true;break;
                                case 7:if (obj == SatTra7) iFound = true;break;
                                case 8:if (obj == SatTra8) iFound = true;break;
                                case 9:if (obj == SatTra9) iFound = true;break;
                                }
                                if (iFound)
                                {
                                    tg.removeChild(nIndex);
                                    switch(iSat)
                                    {
                                    case 0:SatTra0 = new BranchGroup();SatTra0.setCapability(BranchGroup.ALLOW_DETACH);  SatTra0.addChild(new Shape3D(Sat0Lines));SatDot = new Shape3D(Sat0Dot);SatDot.setAppearance(aSatDot);SatTra0.addChild(SatDot);tg.addChild(SatTra0);break;
                                    case 1:SatTra1 = new BranchGroup();SatTra1.setCapability(BranchGroup.ALLOW_DETACH);  SatTra1.addChild(new Shape3D(Sat1Lines));SatDot = new Shape3D(Sat1Dot);SatDot.setAppearance(aSatDot);SatTra1.addChild(SatDot);tg.addChild(SatTra1);break;
                                    case 2:SatTra2 = new BranchGroup();SatTra2.setCapability(BranchGroup.ALLOW_DETACH);  SatTra2.addChild(new Shape3D(Sat2Lines));SatDot = new Shape3D(Sat2Dot);SatDot.setAppearance(aSatDot);SatTra2.addChild(SatDot);tg.addChild(SatTra2);break;
                                    case 3:SatTra3 = new BranchGroup();SatTra3.setCapability(BranchGroup.ALLOW_DETACH);  SatTra3.addChild(new Shape3D(Sat3Lines));SatDot = new Shape3D(Sat3Dot);SatDot.setAppearance(aSatDot);SatTra3.addChild(SatDot);tg.addChild(SatTra3);break;
                                    case 4:SatTra4 = new BranchGroup();SatTra4.setCapability(BranchGroup.ALLOW_DETACH);  SatTra4.addChild(new Shape3D(Sat4Lines));SatDot = new Shape3D(Sat4Dot);SatDot.setAppearance(aSatDot);SatTra4.addChild(SatDot);tg.addChild(SatTra4);break;
                                    case 5:SatTra5 = new BranchGroup();SatTra5.setCapability(BranchGroup.ALLOW_DETACH);  SatTra5.addChild(new Shape3D(Sat5Lines));SatDot = new Shape3D(Sat5Dot);SatDot.setAppearance(aSatDot);SatTra5.addChild(SatDot);tg.addChild(SatTra5);break;
                                    case 6:SatTra6 = new BranchGroup();SatTra6.setCapability(BranchGroup.ALLOW_DETACH);  SatTra6.addChild(new Shape3D(Sat6Lines));SatDot = new Shape3D(Sat6Dot);SatDot.setAppearance(aSatDot);SatTra6.addChild(SatDot);tg.addChild(SatTra6);break;
                                    case 7:SatTra7 = new BranchGroup();SatTra7.setCapability(BranchGroup.ALLOW_DETACH);  SatTra7.addChild(new Shape3D(Sat7Lines));SatDot = new Shape3D(Sat7Dot);SatDot.setAppearance(aSatDot);SatTra7.addChild(SatDot);tg.addChild(SatTra7);break;
                                    case 8:SatTra8 = new BranchGroup();SatTra8.setCapability(BranchGroup.ALLOW_DETACH);  SatTra8.addChild(new Shape3D(Sat8Lines));SatDot = new Shape3D(Sat8Dot);SatDot.setAppearance(aSatDot);SatTra8.addChild(SatDot);tg.addChild(SatTra8);break;
                                    case 9:SatTra9 = new BranchGroup();SatTra9.setCapability(BranchGroup.ALLOW_DETACH);  SatTra9.addChild(new Shape3D(Sat9Lines));SatDot = new Shape3D(Sat9Dot);SatDot.setAppearance(aSatDot);SatTra9.addChild(SatDot);tg.addChild(SatTra9);break;
                                    }
                                }
                                nIndex++;
                            }
                        }
                    }
                    //JD:_1234567890123456789_time:_00/00/00_00:00:00
                    m_LabelJD.setText("JD: "+String.valueOf(TimeJD));
                    if (TimeJDOld != TimeJD)
                    {
                        m_LabelYYMMDDHHMMSS.setText(" time: " + TimeYYMMDDHHMMSS);
                        Olddelaymsec = delaymsec;
                    }
                    else
                    {
                        m_LabelYYMMDDHHMMSS.setText(" stop: " + TimeYYMMDDHHMMSS);
                        Olddelaymsec +=1000;
                        delaymsec = Olddelaymsec;
                    }
                    //m_TimerCtrl.setText(String.valueOf(TimeJD));
                    //m_LabelYYMMDDHHMMSS.setText(TimeYYMMDDHHMMSS);

                    //m_TimerCtrl.setText(String.valueOf(TimerCount));
                    TimeJDOld = TimeJD;
                 }
                 else
                 {
                     m_LabelJD.setText("unavalable");
                     m_LabelYYMMDDHHMMSS.setText("unavalable");
                     //m_TimerCtrl.setText("unavalable"));
                     //m_TimerCtrl.setText("--");
                 }
             }
             catch(InterruptedException e)
             {
                 e.printStackTrace();  
             }
         }
     }
    public void start()
    {
        XMLTimerread.resume();
    }
    
    public void stop()
    {
        XMLTimerread.suspend();
    }
    //protected BranchGroup createSceneBranchGroup() 
    //{
    //    m_SceneBranchGroup = new BranchGroup();
    //    return m_SceneBranchGroup;
    //}
    protected void addViewBranchGroup(Locale locale, BranchGroup bg) 
    {
        locale.addBranchGraph(bg);
    }
    protected View createView(ViewPlatform vp) 
    {
        View view = new View();
        PhysicalBody pb = new PhysicalBody();
        PhysicalEnvironment pe = new PhysicalEnvironment();
        //AudioDevice audioDevice = createAudioDevice(pe);
        //if (audioDevice != null) 
        //{
        //    pe.setAudioDevice(audioDevice);
        //        audioDevice.initialize();
        //}
        view.setPhysicalEnvironment(pe);
        view.setPhysicalBody(pb);
        if (vp != null)
            view.attachViewPlatform(vp);
        view.setBackClipDistance(getBackClipDistance());
        view.setFrontClipDistance(getFrontClipDistance());
        Canvas3D c3d = createCanvas3D();
        view.addCanvas3D(c3d);
        addCanvas3D(c3d);
        return view;
    }
    protected Canvas3D createCanvas3D() 
    {
        GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
        gc3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
        GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        Canvas3D c3d = new Canvas3D(gd[0].getBestConfiguration(gc3D));
        c3d.setSize(getCanvas3dWidth(c3d), getCanvas3dHeight(c3d));
        return c3d;
    }
    protected int getCanvas3dWidth(Canvas3D c3d) 
    {
        return m_kWidth;
    }
    protected int getCanvas3dHeight(Canvas3D c3d) 
    {
        return m_kHeight;
    }
    protected double getBackClipDistance() 
    {
        return 100.0;
    }
    protected double getFrontClipDistance() 
    {
        return 1.0;
    }
    public TransformGroup[] getViewTransformGroupArray() 
    {
        TransformGroup[] tgArray = new TransformGroup[1];
        tgArray[0] = new TransformGroup();
        // move the camera BACK a little...
        // note that we have to invert the matrix as
        // we are moving the viewer
        Transform3D t3d = new Transform3D();
        t3d.setScale(getScale());
        t3d.setTranslation(new Vector3d(0.0, 0.0, -20.0));
        t3d.invert();
        tgArray[0].setTransform(t3d);
        return tgArray;
    }
    protected double getScale() 
    {
        return 1.0;
    }
    protected BranchGroup createViewBranchGroup(TransformGroup[] tgArray, ViewPlatform vp) 
    {
        BranchGroup vpBranchGroup = new BranchGroup();
        if (tgArray != null && tgArray.length > 0) 
        {
            Group parentGroup = vpBranchGroup;
            TransformGroup curTg = null;
            for (int n = 0; n < tgArray.length; n++) 
            {
                curTg = tgArray[n];
                parentGroup.addChild(curTg);
                parentGroup = curTg;
            }
            tgArray[tgArray.length - 1].addChild(vp);
        } 
        else
            vpBranchGroup.addChild(vp);
        return vpBranchGroup;
    }
    protected ViewPlatform createViewPlatform() 
    {
        ViewPlatform vp = new ViewPlatform();
        vp.setViewAttachPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
        vp.setActivationRadius(getViewPlatformActivationRadius());
        return vp;
    }
    protected float getViewPlatformActivationRadius() 
    {
        return 100;
    }
    public void onStartDrag(Object target) 
    {
    }
    public void onEndDrag(Object target) 
    {
    }
    public void onApplyTransform(Object target) 
    {
    }
    public void onAdjustTransform(Object target, int xpos, int ypos) 
    {
    }
    // called by TornadoMouseRotate
    // yes, those really are Euler angles for the objects rotation
    public void onRotate(Object target, Point3d point3d) 
    {
        //m_RotationFieldX.setText(String.valueOf((int) java.lang.Math.toDegrees(point3d.x)));
        //m_RotationFieldY.setText(String.valueOf((int) java.lang.Math.toDegrees(point3d.y)));
        //m_RotationFieldZ.setText(String.valueOf((int) java.lang.Math.toDegrees(point3d.z)));
    }
    // called by TornadoMouseScale
    public void onScale(Object target, Vector3d scale) 
    {
        m_ScaleFromMouse = scale.x;
        //m_ScaleFieldX.setText(String.valueOf(scale.x));
        //m_ScaleFieldY.setText(String.valueOf(scale.y));
        //m_ScaleFieldZ.setText(String.valueOf(scale.z));
    }
    // called by TornadoMouseTranslate
    public void onTranslate(Object target, Vector3d vTranslation) 
    {
        //m_TranslationFieldX.setText(String.valueOf(vTranslation.x));
        //m_TranslationFieldY.setText(String.valueOf(vTranslation.y));
        //m_TranslationFieldZ.setText(String.valueOf(vTranslation.z));
    }
    // we want a black background
    protected Background createBackground() 
    {
        return null;
    }
    
    protected void addCanvas3D(Canvas3D c3d) 
    {
        setLayout(new BorderLayout());
        add(c3d, BorderLayout.CENTER);
        Panel controlPanel = new Panel();
        // add the UI to the frame
        //m_RotationLabel = new Label("Rot: ");
        //m_RotationFieldX = new TextField("0.00");
        //m_RotationFieldY = new TextField("0.00");
        //m_RotationFieldZ = new TextField("0.00");
        
        //controlPanel.add(m_RotationLabel);
        //controlPanel.add(m_RotationFieldX);
        //controlPanel.add(m_RotationFieldY);
        //controlPanel.add(m_RotationFieldZ);
        //m_TranslationLabel = new Label("Trn: ");
        //m_TranslationFieldX = new TextField("0.00");
        //m_TranslationFieldY = new TextField("0.00");
        //m_TranslationFieldZ = new TextField("0.00");
        //controlPanel.add(m_TranslationLabel);
        //controlPanel.add(m_TranslationFieldX);
        //controlPanel.add(m_TranslationFieldY);
        //controlPanel.add(m_TranslationFieldZ);
        //m_ScaleLabel = new Label("Scl: ");
        //m_ScaleFieldX = new TextField("0.00");
        //m_ScaleFieldY = new TextField("0.00");
        //m_ScaleFieldZ = new TextField("0.00");
        //controlPanel.add(m_ScaleLabel);
        //controlPanel.add(m_ScaleFieldX);
        //controlPanel.add(m_ScaleFieldY);
        //controlPanel.add(m_ScaleFieldZ);
        
        m_EarthView = new Button("Earth View");
        controlPanel.add(m_EarthView);
        m_MoonView = new Button("Moon View");
        controlPanel.add(m_MoonView);
        
        //m_ViewYZ = new Button("Rot Z");
        //controlPanel.add(m_ViewYZ);
        //m_ViewXZ = new Button("X-Z");
        //controlPanel.add(m_ViewXZ);
        //m_ViewXY = new Button("X-Y");
        //controlPanel.add(m_ViewXY);
        m_LabelJD = new Label("JD:_1234567890123456789");//
        
        controlPanel.add(m_LabelJD);
        //m_TimerCtrl = new TextField("00000000000");
        //m_TimerCtrl.setSize(m_TimerCtrl.getWidth()*4, m_TimerCtrl.getHeight());
        //controlPanel.add(m_TimerCtrl);
        m_LabelYYMMDDHHMMSS = new Label(" time:_00/00/00_00:00:00");
        controlPanel.add(m_LabelYYMMDDHHMMSS);
        
        add(controlPanel, BorderLayout.SOUTH);
        m_EarthView.addActionListener(this);
        m_MoonView.addActionListener(this);
        //m_ViewYZ.addActionListener(this);
        //m_ViewXZ.addActionListener(this);
        //m_ViewXY.addActionListener(this);

       
        doLayout();
        
    }

    public void destroy() 
    {
        XMLTimerread.stop();
	u.cleanup();
    }

    //
    // The following allows SphereMotion to be run as an application
    // as well as an applet
    //
    public static void main(String[] args) {
        // Parse the Input Arguments
	String usage = "Usage: java SphereMotion [-point | -spot | -dir]";
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (args[i].equals("-point")) {
		    System.out.println("Using point lights");
                    lightType = POINT_LIGHT;
                }
		else if (args[i].equals("-spot")) {
		    System.out.println("Using spot lights");
                    lightType = SPOT_LIGHT;
                }
		else if (args[i].equals("-dir")) {
		    System.out.println("Using directional lights");
                    lightType = DIRECTIONAL_LIGHT;
                }
		else {
		    System.out.println(usage);
                    System.exit(0);
                }
            }
	    else {
		System.out.println(usage);
		System.exit(0);
	    }
        }

	new MainFrame(new visualtra(), m_kWidth, m_kHeight);
    }
}

//*****************************************************************************
// TornadoMouseRotate
// 
// Custom mouse rotation behaviour
// @author Daniel Selman@version 1.0
//*****************************************************************************
class TornadoMouseRotate extends TornadoMouseBehavior 
{
    protected double m_FactorX = 0.001;
    protected double m_FactorY = 0.001;
    protected Transform3D m_TransformX = null;
    protected Transform3D m_TransformY = null;
    protected boolean m_bInvert = false;
    //*****************************************************************************
    // @param xf the x rotation scale factor
    // @param yf the y rotation scale factor
    //*****************************************************************************
    public TornadoMouseRotate(double xf, double yf) 
    {
        m_FactorX = xf;
        m_FactorY = yf;
        m_TransformX = new Transform3D();
        m_TransformY = new Transform3D();
        m_bInvert = false;
    }
    protected boolean isStartBehaviorEvent(java.awt.event.MouseEvent evt) 
    {
        int nId = evt.getID();
        return ((nId == MouseEvent.MOUSE_DRAGGED) && (evt.isAltDown() == false) && (evt.isMetaDown() == false));
    }
    //*****************************************************************************
    // @param bInvert            true to invert the Y axis
    //*****************************************************************************
    public void setInvert(boolean bInvert) 
    {
        m_bInvert = bInvert;
    }
    // this behavior is relative to the *screen*
    // the current rotation of the object etc. is ignored
    protected boolean isRelativeToObjectCoordinates() 
    {
        return false;
    }
    protected void applyVectorToObject(Vector3f vector) 
    {
        TransformGroup tg = getTransformGroup();
        if (tg != null) 
        {
            tg.getTransform(m_Transform3D);
            double x_angle = 0;//vector.y * m_FactorX;
            double y_angle = vector.x * m_FactorY;
            m_TransformX.rotX(x_angle);
            m_TransformY.rotY(y_angle);
            Matrix4d mat = new Matrix4d();
            // Remember old matrix
            m_Transform3D.get(mat);
            // Translate to origin
            m_Transform3D.setTranslation(new Vector3d(0.0, 0.0, 0.0));
            if (m_bInvert != false) 
            {
                m_Transform3D.mul(m_Transform3D, m_TransformX);
                m_Transform3D.mul(m_Transform3D, m_TransformY);
            } 
            else 
            {
                m_Transform3D.mul(m_TransformX, m_Transform3D);
                m_Transform3D.mul(m_TransformY, m_Transform3D);
            }
            // Set old translation back
            Vector3d translation = new Vector3d(mat.m03, mat.m13, mat.m23);
            m_Transform3D.setTranslation(translation);
            // save the new Transform3D
            applyTransform();
            if (m_Listener != null) 
            {
                Point3d rotate = Euler.getEulerRotation(m_Transform3D);
                ((RotationChangeListener) m_Listener).onRotate(m_Object, rotate);
            }
        }
    }
} // TornadoMouseRotate
class Euler 
{
    public static int EulOrdXYZs() 
    {
        return EulOrd(X, EulParEven, EulRepNo, EulFrmS);
    }
    public static int EulOrdXYXs() 
    {
        return EulOrd(X, EulParEven, EulRepYes, EulFrmS);
    }
    public static int EulOrdXZYs() 
    {
        return EulOrd(X, EulParOdd, EulRepNo, EulFrmS);
    }
    public static int EulOrdXZXs() 
    {
        return EulOrd(X, EulParOdd, EulRepYes, EulFrmS);
    }
    public static int EulOrdYZXs() 
    {
        return EulOrd(Y, EulParEven, EulRepNo, EulFrmS);
    }
    public static int EulOrdYZYs() 
    {
        return EulOrd(Y, EulParEven, EulRepYes, EulFrmS);
    }
    public static int EulOrdYXZs() 
    {
        return EulOrd(Y, EulParOdd, EulRepNo, EulFrmS);
    }
    public static int EulOrdYXYs() 
    {
        return EulOrd(Y, EulParOdd, EulRepYes, EulFrmS);
    }
    public static int EulOrdZXYs() 
    {
        return EulOrd(Z, EulParEven, EulRepNo, EulFrmS);
    }
    public static int EulOrdZXZs() 
    {
        return EulOrd(Z, EulParEven, EulRepYes, EulFrmS);
    }
    public static int EulOrdZYXs() 
    {
        return EulOrd(Z, EulParOdd, EulRepNo, EulFrmS);
    }
    public static int EulOrdZYZs() 
    {
        return EulOrd(Z, EulParOdd, EulRepYes, EulFrmS);
    }
    /* Rotating axes */
    public static int EulOrdZYXr() 
    {
        return EulOrd(X, EulParEven, EulRepNo, EulFrmR);
    }
    public static int EulOrdXYXr() 
    {
        return EulOrd(X, EulParEven, EulRepYes, EulFrmR);
    }
    public static int EulOrdYZXr() 
    {
        return EulOrd(X, EulParOdd, EulRepNo, EulFrmR);
    }
    public static int EulOrdXZXr() 
    {
        return EulOrd(X, EulParOdd, EulRepYes, EulFrmR);
    }
    public static int EulOrdXZYr() 
    {
        return EulOrd(Y, EulParEven, EulRepNo, EulFrmR);
    }
    public static int EulOrdYZYr() 
    {
        return EulOrd(Y, EulParEven, EulRepYes, EulFrmR);
    }
    public static int EulOrdZXYr() 
    {
        return EulOrd(Y, EulParOdd, EulRepNo, EulFrmR);
    }
    public static int EulOrdYXYr() 
    {
        return EulOrd(Y, EulParOdd, EulRepYes, EulFrmR);
    }
    public static int EulOrdYXZr() 
    {
        return EulOrd(Z, EulParEven, EulRepNo, EulFrmR);
    }
    public static int EulOrdZXZr() 
    {
        return EulOrd(Z, EulParEven, EulRepYes, EulFrmR);
    }
    public static int EulOrdXYZr() 
    {
        return EulOrd(Z, EulParOdd, EulRepNo, EulFrmR);
    }
    public static int EulOrdZYZr() 
    {
        return EulOrd(Z, EulParOdd, EulRepYes, EulFrmR);
    }
    public static int EulFrm(int ord) 
    {
        // DCS, was unsigned
        return ((ord) & 1);
    }
    public static int EulRep(int ord) 
    {
        // DCS, was unsigned
        return (((ord) >> 1) & 1);
    }
    public static int EulPar(int ord) 
    {
        return (((ord) >> 2) & 1);
    }
    public static int EulAxI(int ord) 
    {
        // DCS, was unsigned
        return ((int) (EulSafe((((ord) >> 3) & 3))));
    }
    public static int EulAxJ(int ord) 
    {
        int i = 0;
        if (EulPar(ord) == EulParOdd)
            i = 1;
        return ((int) (EulNext(EulAxI(ord) + i)));
    }
    public static int EulAxK(int ord) 
    {
        int i = 0;
        if (EulPar(ord) != EulParOdd)
            i = 1;
        return ((int) (EulNext(EulAxI(ord) + i)));
    }
    public static int EulAxH(int ord) 
    {
        if (EulRep(ord) == EulRepNo)
            return EulAxK(ord);
        return EulAxI(ord);
    }
    public static int EulOrd(int i, int p, int r, int f) 
    {
        return (((((((i) << 1) + (p)) << 1) + (r)) << 1) + (f));
    }
    // enum
    static final int X = 0;
    static final int Y = 1;
    static final int Z = 2;
    static final int W = 3;
    static final int EulRepNo = 0;
    static final int EulRepYes = 1;
    static final int EulParEven = 0;
    static final int EulParOdd = 1;
    static final int EulFrmS = 0;
    static final int EulFrmR = 1;
    static final float FLT_EPSILON = 1.192092896e-07F;
    static EulGetOrdInfo EulGetOrd(int ord) 
    {
        EulGetOrdInfo info = new EulGetOrdInfo();
        // note, used to be unsigned!
        int o = ord;
        info.f = o & 1;
        o >>= 1;
        info.s = o & 1;
        o >>= 1;
        info.n = o & 1;
        o >>= 1;
        info.i = EulSafe(o & 3);
        info.j = EulNext(info.i + info.n);
        info.k = EulNext(info.i + 1 - info.n);
        if (info.s != 0)
            info.h = info.k;
        else
            info.h = info.i;
        return info;
    }
    static int EulSafe(int val) 
    {
        int[] valArray = { 0, 1, 2, 0 };
        return valArray[val];
    }
    static int EulNext(int val) 
    {
        int[] valArray = { 1, 2, 0, 1 };
        return valArray[val];
    }
    // float HMatrix[4][4];
    /* Convert matrix to Euler angles (in radians). */
    public static EulerAngles Eul_FromMatrix(float[][] M, int order) 
    {
        EulerAngles ea = new EulerAngles();
        EulGetOrdInfo info = EulGetOrd(order);
        int i = info.i;
        int j = info.j;
        int k = info.k;
        int h = info.h;
        int n = info.n;
        int s = info.s;
        int f = info.f;
        if (s == EulRepYes) 
        {
            double sy = Math.sqrt(M[i][j] * M[i][j] + M[i][k] * M[i][k]);
            if (sy > 16 * FLT_EPSILON) 
            {
                ea.x = (float) Math.atan2(M[i][j], M[i][k]);
                ea.y = (float) Math.atan2(sy, M[i][i]);
                ea.z = (float) Math.atan2(M[j][i], -M[k][i]);
            } 
            else 
            {
                ea.x = (float) Math.atan2(-M[j][k], M[j][j]);
                ea.y = (float) Math.atan2(sy, M[i][i]);
                ea.z = 0;
            }
        } 
        else 
        {
            double cy = Math.sqrt(M[i][i] * M[i][i] + M[j][i] * M[j][i]);
            if (cy > 16 * FLT_EPSILON) 
            {
                ea.x = (float) Math.atan2(M[k][j], M[k][k]);
                ea.y = (float) Math.atan2(-M[k][i], cy);
                ea.z = (float) Math.atan2(M[j][i], M[i][i]);
            } 
            else 
            {
                ea.x = (float) Math.atan2(-M[j][k], M[j][j]);
                ea.y = (float) Math.atan2(-M[k][i], cy);
                ea.z = 0;
            }
        }
        if (n == EulParOdd) 
        {
            ea.x = -ea.x;
            ea.y = -ea.y;
            ea.z = -ea.z;
        }
        if (f == EulFrmR) 
        {
            float t = ea.x;
            ea.x = ea.z;
            ea.z = t;
        }
        ea.w = order;
        return (ea);
    }
    /* Convert quaternion to Euler angles (in radians). */
    public static EulerAngles Eul_FromQuat(Quat q, int order) 
    {
        float[][] M = new float[4][4];
        double Nq = q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w;
        double s = (Nq > 0.0) ? (2.0 / Nq) : 0.0;
        double xs = q.x * s, ys = q.y * s, zs = q.z * s;
        double wx = q.w * xs, wy = q.w * ys, wz = q.w * zs;
        double xx = q.x * xs, xy = q.x * ys, xz = q.x * zs;
        double yy = q.y * ys, yz = q.y * zs, zz = q.z * zs;
        M[X][X] = (float) (1.0 - (yy + zz));
        M[X][Y] = (float) (xy - wz);
        M[X][Z] = (float) (xz + wy);
        M[Y][X] = (float) (xy + wz);
        M[Y][Y] = (float) (1.0 - (xx + zz));
        M[Y][Z] = (float) (yz - wx);
        M[Z][X] = (float) (xz - wy);
        M[Z][Y] = (float) (yz + wx);
        M[Z][Z] = (float) (1.0 - (xx + yy));
        M[W][X] = M[W][Y] = M[W][Z] = M[X][W] = M[Y][W] = M[Z][W] = 0.0f;
        M[W][W] = 1.0f;
        return (Eul_FromMatrix(M, order));
    }
    public static Point3d getEulerRotation(Transform3D t3d) 
    {
        Point3d Rotation = new Point3d();
        Matrix3d m1 = new Matrix3d();
        t3d.get(m1);
        // extract the rotation angles from the upper 3x3 rotation
        // component of the 4x4 transformation matrix
        Rotation.y = -java.lang.Math.asin(m1.getElement(2, 0));
        double c = java.lang.Math.cos(Rotation.y);
        double tRx, tRy, tRz;
        if (java.lang.Math.abs(Rotation.y) > 0.00001) 
        {
            tRx = m1.getElement(2, 2) / c;
            tRy = -m1.getElement(2, 1) / c;
            Rotation.x = java.lang.Math.atan2(tRy, tRx);
            tRx = m1.getElement(0, 0) / c;
            tRy = -m1.getElement(1, 0) / c;
            Rotation.z = java.lang.Math.atan2(tRy, tRx);
        } 
        else 
        {
            Rotation.x = 0.0;
            tRx = m1.getElement(1, 1);
            tRy = m1.getElement(0, 1);
            Rotation.z = java.lang.Math.atan2(tRy, tRx);
        }
        Rotation.x = -Rotation.x;
        Rotation.z = -Rotation.z;
        // now try to ensure that the values are positive by adding 2PI if
        // necessary...
        if (Rotation.x < 0.0)
            Rotation.x += 2 * java.lang.Math.PI;
        if (Rotation.y < 0.0)
            Rotation.y += 2 * java.lang.Math.PI;
        if (Rotation.z < 0.0)
            Rotation.z += 2 * java.lang.Math.PI;
        return Rotation;
    }
}
class EulerAngles extends Quat 
{
    public EulerAngles() 
    {
    }
}
class Quat
{
    public float x;
    public float y;
    public float z;
    public float w;
    public Quat( )
    {
    }
}
class EulGetOrdInfo 
{
    public int i;
    public int j;
    public int k;
    public int h;
    public int n;
    public int s;
    public int f;
    EulGetOrdInfo() 
    {
    }
}
//*****************************************************************************
// Base class for the Tornado Mouse Behaviors (Rotate, Translate, Scale).
// 
// @author Daniel Selman @version 1.0
//*****************************************************************************
abstract class TornadoMouseBehavior extends Behavior 
{
    // private data
    protected Object m_Object = null;
    protected Point3f m_NewPos = null;
    protected Point3f m_OldPos = null;
    protected Vector3f m_TranslationVector = null;
    protected Transform3D m_Translation = null;
    protected boolean m_bDragging = false;
    protected WakeupOr m_MouseCriterion = null;
    protected int m_nLastY = 0;
    protected Transform3D m_Transform3D = null;
    protected TornadoChangeListener m_Listener = null;
    public TornadoMouseBehavior() 
    {
        m_Object = null;
        m_NewPos = new Point3f();
        m_OldPos = new Point3f();
        m_Translation = new Transform3D();
        m_TranslationVector = new Vector3f();
        m_bDragging = false;
        m_Transform3D = new Transform3D();
    }
    //*****************************************************************************
    //Register a listener for the behavior.
    // 
    // @param listener            the listener to add or null to remove the listener
    //*****************************************************************************
    public void setChangeListener(TornadoChangeListener listener) 
    {
        m_Listener = listener;
    }
    //*****************************************************************************
    // Apply a delta vector (in the object"s local coordinates) to the object.
    //*****************************************************************************
    protected abstract void applyVectorToObject(Vector3f v);
    //*****************************************************************************
    // @return true is this the mouse event that starts the tracking behaviour
    //*****************************************************************************
    protected abstract boolean isStartBehaviorEvent(java.awt.event.MouseEvent evt);
    //*****************************************************************************
    // Dispatches mouse events as appropriate. Should not need to overide this
    // method.
    //*****************************************************************************
    protected void processMouseEvent(java.awt.event.MouseEvent evt) 
    {
        if (m_Object != null) 
        {
            if (isStartBehaviorEvent(evt) != false)
                adjustTransform(evt.getX(), evt.getY());
            else if (isStopBehaviorEvent(evt) != false)
                    onEndDrag();
        }
    }
    //*****************************************************************************
    // @return true if this is the event that stops drag tracking behviour the
    //         default uses MOUSE_RELEASED.
    //*****************************************************************************
    protected boolean isStopBehaviorEvent(java.awt.event.MouseEvent evt) 
    {
        int nId = evt.getID();
        return (m_bDragging != false && nId == MouseEvent.MOUSE_RELEASED || nId == MouseEvent.MOUSE_EXITED);
    }
    //*****************************************************************************
    // @return true if this behaviours change vector is relative to the starting
    //         mouse click. The default is a behaviour that generates delta
    //         change vectors as the user moves the mouse.
    //*****************************************************************************
    protected boolean isRelativeToStartDrag() 
    {
        return false;
    }
    //*****************************************************************************
    //@return true if the mouse coordinates should be converted to local object
    //         coordinates before being processed by applyVectorToObject
    //*****************************************************************************
    protected boolean isRelativeToObjectCoordinates() 
    {
        return true;
    }
    //*****************************************************************************
    // Allows custom start drag processing. Default does nothing.
    //*****************************************************************************
    protected void onStartDrag() 
    {
        if (m_Listener != null)
            m_Listener.onStartDrag(m_Object);
    }
    //*****************************************************************************
    // Allows custom end drag processing. ** Call this base class! **
    //*****************************************************************************
    protected void onEndDrag() 
    {
        m_bDragging = false;
        if (m_Listener != null)
            m_Listener.onEndDrag(m_Object);
    }
    //*****************************************************************************
    // Gets the Transform3D to convert from the Objects coordinate system to the
    // world coordinate system.
    // 
    // @param t3d            the Transform3D to populate
    //*****************************************************************************
    protected void getObjectLocalToVworld(Transform3D t3d) 
    {
        if (getTransformGroup() != null)
            getTransformGroup().getLocalToVworld(t3d);
    }
    //*****************************************************************************
    // Gets the Transform3D to convert from the Image plate coordinate system to
    // the world coordinate system.
    // 
    // @param t3d            the Transform3D to populate
    //*****************************************************************************
    protected void getImagePlateToVworld(Transform3D t3d) 
    {
        getView().getCanvas3D(0).getImagePlateToVworld(t3d);
    }
    //*****************************************************************************
    // @return the TransformGroup if a TG Object is associated with the behavior
    //         or null otherwise.
    //*****************************************************************************
    protected TransformGroup getTransformGroup() 
    {
        if (m_Object instanceof TransformGroup)
            return (TransformGroup) m_Object;
        return null;
    }
    //*****************************************************************************
    // Saves the behaviors Transform3D into its TransformGroup (if present).
    // Catches any exceptions (bad transform) that might be thrown.
    //*****************************************************************************
    protected void applyTransform() 
    {
        TransformGroup tg = getTransformGroup();
        if (tg != null) 
        {
            try 
            {
                // save the new Transform3D
                tg.setTransform(m_Transform3D);
                if (m_Listener != null)
                    m_Listener.onApplyTransform(m_Object);
            } 
            catch (Exception e) 
            {
                System.err.println(e.toString());
            }
        }
    }
    //*****************************************************************************
    // Transforms the x,y mouse coordinates to coordinates relative to the
    // object. Calculates a "delta vector" in object coordinates and calls
    // ApplyVectorToObject().
    // 
    // Thanks to: A.R. van Ballegooy. Simon McMullen [simonmc@mincom.ru]
    //*****************************************************************************
    protected void adjustTransform(int xpos, int ypos) 
    {
        if (m_Listener != null)
            m_Listener.onAdjustTransform(m_Object, xpos, ypos);
        if (m_bDragging == false) 
        {
            // initialise the starting position
            m_OldPos.x = xpos;
            m_OldPos.y = ypos;
            m_OldPos.z = 0.0f;
            m_nLastY = ypos;
            onStartDrag();
        }
        m_bDragging = true;
        // save the current position and invert the tracking in the Y direction
        // (positive upwards)
        m_NewPos.x = xpos;
        m_NewPos.y = m_nLastY + (m_nLastY - ypos);
        m_NewPos.z = 0.0f;
        m_nLastY = ypos;
        // transform points to Virtual World Coordinates
        getImagePlateToVworld(m_Translation);
        if (isRelativeToStartDrag() == false)
            m_Translation.transform(m_OldPos);
        m_Translation.transform(m_NewPos);
        // transform coordinates to Object Space Coordinates
        // Make sure capability ALLOW_LOCAL_TO_VWORLD_READ is set....
        if (isRelativeToObjectCoordinates() != false) 
        {
            getObjectLocalToVworld(m_Translation);
            m_Translation.transpose();
            // transform points to local coordinate system
            if (isRelativeToStartDrag() == false)
                m_Translation.transform(m_OldPos);
            m_Translation.transform(m_NewPos);
        }
        // Calculate change and scale
        m_TranslationVector.sub(m_NewPos, m_OldPos);
        //m_OriginalPosition.sub(m_OldPos, m_NewPos );
        applyVectorToObject(m_TranslationVector);
        if (isRelativeToStartDrag() == false) 
        {
            // store the new positions
            m_OldPos.x = xpos;
            m_OldPos.y = ypos;
            m_OldPos.z = 0.0f;
        }
    }
    //*****************************************************************************
    // Dispatches events based on the behaviours criteria
    //*****************************************************************************
    public void processStimulus(Enumeration criteria) 
    {
        WakeupCriterion wakeup;
        AWTEvent[] event;
        int id;
        int dx, dy;
        if (m_Object != null) 
        {
            while (criteria.hasMoreElements()) 
            {
                wakeup = (WakeupCriterion) criteria.nextElement();
                if (wakeup instanceof WakeupOnAWTEvent) 
                {
                    event = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
                    for (int i = 0; i < event.length; i++) 
                    {
                        processMouseEvent((MouseEvent) event[i]);
                    }
                }
            }
        }
        // tell the behaviour when to wake up again...
        wakeupOn(m_MouseCriterion);
    }
    //*****************************************************************************
    //Registers which AWT events are of interest to the behaviour
    //*****************************************************************************
    public void initialize() 
    {
        WakeupCriterion[] mouseEvents = new WakeupCriterion[3];
        mouseEvents[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
        mouseEvents[1] = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
        mouseEvents[2] = new WakeupOnAWTEvent(MouseEvent.MOUSE_RELEASED);
        m_MouseCriterion = new WakeupOr(mouseEvents);
        wakeupOn(m_MouseCriterion);
    }
    //*****************************************************************************
    // void setObject( Object obj )
    // 
    // @param obj            the Objectto manipulate. A null object disables the behaviour.
    //*****************************************************************************
    public void setObject(Object obj) 
    {
        m_Object = obj;
    }
} // TornadoMouseBehavior
//*****************************************************************************
// TornadoMouseScale
// 
// Custon scaling behaviour
// @author Daniel Selman @version 1.0
//*****************************************************************************
class TornadoMouseScale extends TornadoMouseBehavior 
{
    // private data
    protected float m_Delta = 0;
    protected float m_Threshold = 0;
    protected Point3d m_MinScale;
    protected Point3d m_MaxScale;
    //*****************************************************************************
    // @param threshold        the amount the mouse must be moved before an object is moved
    // @param delta            the step size to use for object scaling bigger = faster
    //                         scaling.
    // 
    // Default minimum scale: 0.1,0.1,0.1 Default maximum scale: 5,5,5
    //*****************************************************************************
    public TornadoMouseScale(float threshold, float delta) 
    {
        m_Delta = delta;
        m_Threshold = threshold;
        m_MinScale = new Point3d(0.1, 0.1, 0.1);
        m_MaxScale = new Point3d(5, 5, 5);
    }
    //*****************************************************************************
    // @param minScale            the minimum x,y,z scale
    //*****************************************************************************
    public void setMinScale(Point3d minScale) 
    {
        m_MinScale = minScale;
    }
    //*****************************************************************************
    // @param maxScale           the maximum x,y,z scale
    //*****************************************************************************
    public void setMaxScale(Point3d maxScale) 
    {
        m_MaxScale = maxScale;
    }
    // this behavior is relative to the *screen*
    // the current rotation of the object etc. is ignored
    protected boolean isRelativeToObjectCoordinates() 
    {
        return true;
    }
    protected boolean isStartBehaviorEvent(java.awt.event.MouseEvent evt) 
    {
        int nId = evt.getID();
        return ((nId == MouseEvent.MOUSE_DRAGGED) && (evt.isAltDown() != false) && (evt.isMetaDown() == false));
    }
    protected void applyVectorToObject(Vector3f vector) 
    {
        TransformGroup tg = getTransformGroup();
        if (tg != null) 
        {
            tg.getTransform(m_Transform3D);
            Vector3d vScale = new Vector3d();
            m_Transform3D.getScale(vScale);
            Vector3f delta = new Vector3f();
            if (vector.x > m_Threshold)
                delta.x = m_Delta;
            else if (vector.x < -m_Threshold)
                    delta.x = -m_Delta;
            if (vector.y > m_Threshold)
                delta.y = m_Delta;
            else if (vector.y < -m_Threshold)
                    delta.y = -m_Delta;
            if (vector.z > m_Threshold)
                delta.z = m_Delta;
            else if (vector.z < -m_Threshold)
                    delta.z = -m_Delta;
            //Vector3d objectScale = new Vector3d(vScale.x + delta.x, vScale.y + delta.y, vScale.z + delta.z);
            Vector3d objectScale = new Vector3d(vScale.x + delta.x, vScale.x + delta.x, vScale.x + delta.x);
            if (objectScale.x >= m_MinScale.x && objectScale.y >= m_MinScale.y && objectScale.z >= m_MinScale.z) 
            {
                if (objectScale.x <= m_MaxScale.x && objectScale.y <= m_MaxScale.y && objectScale.z <= m_MaxScale.z) 
                {
                    m_Transform3D.setScale(objectScale);
                    // save the new Transform3D
                    applyTransform();
                    if (m_Listener != null)
                        ((ScaleChangeListener) m_Listener).onScale(m_Object, objectScale);
                }
            }
        }
    }
} // TornadoMouseScale
//*****************************************************************************
// TornadoMouseTranslate
// 
// Custom translation behavior.
// 
// @author Daniel Selman@version 1.0
//*****************************************************************************
class TornadoMouseTranslate extends TornadoMouseBehavior 
{
    // private data
    private float m_Scale = 1;
    protected Point3d m_MinTranslate = null;
    protected Point3d m_MaxTranslate = null;
    // protected data
    // public data
    //*****************************************************************************
    // @param scale           the translation scale factor (bigger = faster)
    // 
    // Default minimum translation: -10,-10,-10 Default maximum translation:
    // 10,10,10
    //*****************************************************************************
    public TornadoMouseTranslate(float scale) 
    {
        m_Scale = scale;
        m_MinTranslate = new Point3d(-10, -10, -10);
        m_MaxTranslate = new Point3d(10, 10, 10);
    }
    //*****************************************************************************
    // @param minTrans            the minimum x,y,z translation
    //*****************************************************************************
    public void setMinTranslate(Point3d minTrans) 
    {
        m_MinTranslate = minTrans;
    }
    //*****************************************************************************
    // @param maxTrans            the maximum x,y,z translation
    //*****************************************************************************
    public void setMaxTranslate(Point3d maxTrans) 
    {
        m_MaxTranslate = maxTrans;
    }
    protected boolean isStartBehaviorEvent(java.awt.event.MouseEvent evt) 
    {
        int nId = evt.getID();
        return ((nId == MouseEvent.MOUSE_DRAGGED) && (evt.isAltDown() == false) && (evt.isMetaDown() != false));
    }
    protected void applyVectorToObject(Vector3f vector) 
    {
        TransformGroup tg = getTransformGroup();
        if (tg != null) 
        {
            // scale the mouse movements so the objects roughly tracks with the
            // mouse
            vector.scale(m_Scale);
            Vector3d vTranslation = new Vector3d();
            tg.getTransform(m_Transform3D);
            m_Transform3D.get(vTranslation);
            vTranslation.x += vector.x;
            vTranslation.y += vector.y;
            vTranslation.z += vector.z;
            //if (vTranslation.x >= m_MinTranslate.x && vTranslation.y >= m_MinTranslate.y && vTranslation.z >= m_MinTranslate.z) 
            {
                //if (vTranslation.x <= m_MaxTranslate.x && vTranslation.y <= m_MaxTranslate.y && vTranslation.z <= m_MaxTranslate.z) 
                {
                    m_Transform3D.setTranslation(vTranslation);
                    applyTransform();
                    if (m_Listener != null)
                        ((TranslationChangeListener) m_Listener).onTranslate(m_Object, vTranslation);
                }
            }
        }
    }
} // TornadoMouseTranslate
//*****************************************************************************
// Interface to listen for changes in translation from the TornadoMouseScale
// class.
//
// @author Daniel Selman @version 1.0
//*****************************************************************************
abstract interface ScaleChangeListener extends TornadoChangeListener 
{
    //*****************************************************************************
    // Callback to notify of new scale being applied.
    // 
    // @param target           the Object being manipulated
    // @param scale            the new scale being applied
    //*****************************************************************************
    public void onScale(Object target, Vector3d scale);
}
//*****************************************************************************
// Interface to listen for changes in translation from the TornadoMouseTranslate
// class.
// 
// @author Daniel Selman
// @version 1.0
//*****************************************************************************
abstract interface TranslationChangeListener extends TornadoChangeListener 
{
    //*****************************************************************************
    // Callback to notify of new translationg being applied.
    // @param target            the Object being manipulated
    // @param vTranslation      the new translation being applied
    //*****************************************************************************
    public void onTranslate(Object target, Vector3d vTranslation);
}
//*****************************************************************************
// Interface to listen for changes in rotation from the TornadoMouseRotate
// class.
// 
// @author Daniel Selman @version 1.0
//*****************************************************************************
abstract interface RotationChangeListener extends TornadoChangeListener 
{
    //*****************************************************************************
    // Callback to notify of new translationg being applied.
    // 
    // @param target           the Object being manipulated
    // @param point3d          the new rotation (Euler, radians) applied
    //*****************************************************************************
    public void onRotate(Object target, Point3d point3d);
}
//*****************************************************************************
// Interface to listen for changes affected by in the TornadoMouseBehaviors.
// 
// @author Daniel Selman @version 1.0
//*****************************************************************************
abstract interface TornadoChangeListener 
{
    //*****************************************************************************
    //Callback to notify of a start drag event.
    // 
    // @param target           the Object being manipulated
    //*****************************************************************************
    public void onStartDrag(Object target);
    //*****************************************************************************
    // Callback to notify of an end drag event.
    // 
    // @param target            the Object being manipulated
    //*****************************************************************************
    public void onEndDrag(Object target);
    //*****************************************************************************
    //* Notification that the Transform is being updated
    // 
    // @param target            the Object being manipulated
    //*****************************************************************************
    public void onApplyTransform(Object target);
    //*****************************************************************************
    //Notification that a new Transform is being calculated
    // 
    // @param target            the Object being manipulated
    // @param xpos              the mouse x position
    // @param ypos              the mouse y position
    //*****************************************************************************
    public void onAdjustTransform(Object target, int xpos, int ypos);
}
