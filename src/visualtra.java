

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
  

public class visualtra extends Applet {

    private float EarthX;
    private float EarthY;
    private float EarthZ;
    Texture texEarth;
    
    private float MoonX;
    private float MoonY;
    private float MoonZ;
    
    private float SunX;
    private float SunY;
    private float SunZ;
    
    // Constants for type of light to use
    private static final int DIRECTIONAL_LIGHT = 0;
    private static final int POINT_LIGHT = 1;
    private static final int SPOT_LIGHT = 2;

    // Flag indicates type of lights: directional, point, or spot
    // lights.  This flag is set based on command line argument
    private static int lightType = POINT_LIGHT;

    private SimpleUniverse u = null;

    public BranchGroup createSceneGraph(SimpleUniverse u) {
	Color3f eColor    = new Color3f(0.0f, 0.0f, 0.0f);
	Color3f sColor    = new Color3f(0.0f, 0.0f, 0.0f);
	Color3f objColor  = new Color3f(.6f, .6f, .6f);
        
	Color3f lColor1   = new Color3f(1.0f, 1.0f, 1.0f);
	Color3f lColor2   = new Color3f(0.0f, 0.0f, 1.0f);

        Color3f alColor   = new Color3f(0.2f, 0.2f, 0.2f);
	Color3f bgColor   = new Color3f(0.05f, 0.05f, 0.2f);

	Transform3D t;

	// Create the root of the branch graph
	BranchGroup objRoot = new BranchGroup();

        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        TransformGroup objScale = new TransformGroup();
        Transform3D t3d = new Transform3D();
        //Vector3f vector = new Vector3f(-.2f,.1f , -.4f);
        t3d.setScale(0.4);
        //t3d.setTranslation(vector);
        objScale.setTransform(t3d);
        objRoot.addChild(objScale);

	// Create a bounds for the background and lights
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

	// Set up the background
	Background bg = new Background(bgColor);
	bg.setApplicationBounds(bounds);
	objScale.addChild(bg);

	// Create a Sphere object, generate one copy of the sphere,
	// and add it into the scene graph.
	Material m = new Material(objColor, eColor, objColor, sColor, 100.0f);
        //Material m = new Material();
	Appearance a = new Appearance();
	m.setLightingEnable(true);
	a.setMaterial(m);
        //a.setTexture(texEarth);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.BLEND);//MODULATE);
        a.setTextureAttributes(texAttr);
	Sphere sph = new Sphere(1.5f, Sphere.GENERATE_NORMALS, 180, a);
        
        Transform3D Location = new Transform3D();
        Vector3f vectorEarth = new Vector3f(EarthX,EarthY , EarthZ);
        Location.setTranslation(vectorEarth);
        TransformGroup tg = new TransformGroup(Location);
        
        //Location.setTranslation(vector);
        //tg.setTransform(Location);
        tg.addChild(sph);
        //objScale.setTranslation(vector);
        //t3d.setTranslation(vector);
        objScale.addChild(tg);
	//objScale.addChild(sph);

	// Create the transform group node for the each light and initialize
	// it to the identity.  Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at runtime.  Add them to the root
	// of the subgraph.
	TransformGroup l1RotTrans = new TransformGroup();
	l1RotTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objScale.addChild(l1RotTrans);

	TransformGroup l2RotTrans = new TransformGroup();
	l2RotTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objScale.addChild(l2RotTrans);

	// Create transformations for the positional lights
	t = new Transform3D();
	Vector3d lPos1 =  new Vector3d(0.0, 0.0, 2.0);
	t.set(lPos1);
	TransformGroup l1Trans = new TransformGroup(t);
	l1RotTrans.addChild(l1Trans);

	t = new Transform3D();
	Vector3d lPos2 = new Vector3d(0.5, 0.8, 2.0);
	t.set(lPos2);
	TransformGroup l2Trans = new TransformGroup(t);
	l2RotTrans.addChild(l2Trans);

	// Create Geometry for point lights
	ColoringAttributes caL1 = new ColoringAttributes();
	ColoringAttributes caL2 = new ColoringAttributes();
	caL1.setColor(lColor1);
	caL2.setColor(lColor2);
	Appearance appL1 = new Appearance();
	Appearance appL2 = new Appearance();
	appL1.setColoringAttributes(caL1);
	appL2.setColoringAttributes(caL2);
	l1Trans.addChild(new Sphere(0.05f, appL1));
	l2Trans.addChild(new Sphere(0.05f, appL2));

	// Create lights
	AmbientLight aLgt = new AmbientLight(alColor);

	Light lgt1 = null;
	Light lgt2 = null;

	Point3f lPoint  = new Point3f(0.0f, 0.0f, 0.0f);
	Point3f atten = new Point3f(1.0f, 0.0f, 0.0f);
	Vector3f lDirect1 = new Vector3f(lPos1);
	Vector3f lDirect2 = new Vector3f(lPos2);
	lDirect1.negate();
	lDirect2.negate();

	switch (lightType) {
	case DIRECTIONAL_LIGHT:
	    lgt1 = new DirectionalLight(lColor1, lDirect1);
	    lgt2 = new DirectionalLight(lColor2, lDirect2);
	    break;
	case POINT_LIGHT:
	    lgt1 = new PointLight(lColor1, lPoint, atten);
	    lgt2 = new PointLight(lColor2, lPoint, atten);
	    break;
	case SPOT_LIGHT:
	    lgt1 = new SpotLight(lColor1, lPoint, atten, lDirect1,
				 25.0f * (float)Math.PI / 180.0f, 10.0f);
	    lgt2 = new SpotLight(lColor2, lPoint, atten, lDirect2,
				 25.0f * (float)Math.PI / 180.0f, 10.0f);
	    break;
	}

	// Set the influencing bounds
	aLgt.setInfluencingBounds(bounds);
	lgt1.setInfluencingBounds(bounds);
	lgt2.setInfluencingBounds(bounds);

	// Add the lights into the scene graph
	objScale.addChild(aLgt);
	l1Trans.addChild(lgt1);
	l2Trans.addChild(lgt2);

	// Create a new Behavior object that will perform the desired
	// operation on the specified transform object and add it into the
	// scene graph.
	Transform3D yAxis = new Transform3D();
	Alpha rotor1Alpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
				     0, 0,
				     4000, 0, 0,
				     0, 0, 0);
	RotationInterpolator rotator1 =
	    new RotationInterpolator(rotor1Alpha,
				     l1RotTrans,
				     yAxis,
				     0.0f, (float) Math.PI*2.0f);
	rotator1.setSchedulingBounds(bounds);
	l1RotTrans.addChild(rotator1);

	// Create a new Behavior object that will perform the desired
	// operation on the specified transform object and add it into the
	// scene graph.
	Alpha rotor2Alpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
				     0, 0,
				     1000, 0, 0,
				     0, 0, 0);
	RotationInterpolator rotator2 =
	    new RotationInterpolator(rotor2Alpha,
				     l2RotTrans,
				     yAxis,
				     0.0f, 0.0f);
	bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	rotator2.setSchedulingBounds(bounds);
	l2RotTrans.addChild(rotator2);

	// Create a position interpolator and attach it to the view
	// platform
	TransformGroup vpTrans =
	    u.getViewingPlatform().getViewPlatformTransform();
	Transform3D axisOfTranslation = new Transform3D();
	Alpha transAlpha = new Alpha(-1,
				      Alpha.INCREASING_ENABLE |
				      Alpha.DECREASING_ENABLE,
				      0, 0,
				      5000, 0, 0,
				      5000, 0, 0);
	axisOfTranslation.rotY(-Math.PI/2.0);
	PositionInterpolator translator =
	    new PositionInterpolator(transAlpha,
				     vpTrans,
				     axisOfTranslation,
				     2.0f, 3.5f);
	translator.setSchedulingBounds(bounds);
	objScale.addChild(translator);

        // Let Java 3D perform optimizations on this scene graph.
        objRoot.compile();

	return objRoot;
    }

    public visualtra() {
    }

    public void init() 
    {
        try 
        {  
            //Document doc = parser.getDocument();
            //File fXmlFile = new File("SatCtrl/travisual.xml");  
            
            URL url = new URL("http://24.84.57.253/SatCtrl/travisual.xml");
            InputStream fXmlFile = url.openStream();
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();  
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();  
            Document doc = dBuilder.parse(fXmlFile);  
            doc.getDocumentElement().normalize();  
            //System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("Object");
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
                    
                    NodeList lstYElmntLst = fstElmnt.getElementsByTagName("Y");
                    Element lstYElmnt = (Element) lstYElmntLst.item(0);
                    NodeList lstY = lstYElmnt.getChildNodes();
                    String strY = ((Node) lstY.item(0)).getNodeValue();
                   // System.out.println("Y: " + strY);                    
                    
                    NodeList lstZElmntLst = fstElmnt.getElementsByTagName("Z");
                    Element lstZElmnt = (Element) lstZElmntLst.item(0);
                    NodeList lstZ = lstZElmnt.getChildNodes();
                    String strZ = ((Node) lstZ.item(0)).getNodeValue();
                    //System.out.println("Z: " + strZ);                    
                    
                    if (strType.compareTo("Earth") == 0)
                    {
                        EarthX = Float.valueOf(strX);
                        EarthY= Float.valueOf(strY);
                        EarthZ= Float.valueOf(strZ);
                    }
                    
                    if (strType.compareTo("Moon") == 0)
                    {
                        MoonX = Float.valueOf(strX);
                        MoonY= Float.valueOf(strY);
                        MoonZ= Float.valueOf(strZ);
                    }
                    
                    if (strType.compareTo("Sun") == 0)
                    {
                        SunX = Float.valueOf(strX);
                        SunY= Float.valueOf(strY);
                        SunZ= Float.valueOf(strZ);
                    }
                }
            } 
        } 
        catch (Exception e) 
        {  
            e.printStackTrace();  
        }   
        
        try 
        {
            //TextureLoader texLoader =  new TextureLoader( "SatCtrl/Earth-Color_960_Koord.jpg", "RGBA", this);//new Container());//this );
        
            //URL urlJpg = new URL("http://24.84.57.253/SatCtrl/Earth-Color_960_Koord.jpg");
            //InputStream fJpgFile = urlJpg.openStream();
            //TextureLoader texLoader =  new TextureLoader( fJpgFile, "RGBA", this);//new Container());//this );
            
            
            //TextureLoader texLoader =  new TextureLoader( "SatCtrl/Earth-Color_960_Koord.jpg", "RGBA", this);//new Container());//this );
        
            //TextureLoader texLoader =  new TextureLoader( "Map_Earth_2100_by_JamesVF.jpg","RGB", this);
            //URL myURl = URL("http://192.168.0.102/SatCtrl/Map_Earth_2100_by_JamesVF.jpg");
            //texEarth = new TextureLoader("http://192.168.0.102/SatCtrl/Map_Earth_2100_by_JamesVF.jpg", this).getTexture();
            //ImageComponent myImage = texLoader.getImage();
            //texEarth = new Texture2D();
            //texEarth.setImage(0, myImage);
            //texEarth.setBoundaryModeS(Texture.CLAMP);//.WRAP);
            //texEarth.setBoundaryModeT(Texture.CLAMP);//.WRAP);
            //texEarth.setImage(WIDTH, null);
            //texEarth.setBoundaryColor(new Color4f(0.0f, 1.0f, .0f, 1.0f));
        
            //TextureLoader myLoader = new TextureLoader(”Earth.jpg”);
            //ImageComponent2D myImage = myLoader.getImage();
            // create a Texture2D using the image loaded
            //Texture2D myTexture = new Texture2D();
            //myTexture.setImage(0, myImage);
        } 
        catch (Exception e) 
        {  
            e.printStackTrace();  
        }

	setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D c = new Canvas3D(config);
	add("Center", c);

	u = new SimpleUniverse(c);
	BranchGroup scene = createSceneGraph(u);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        u.getViewingPlatform().setNominalViewingTransform();

	u.addBranchGraph(scene);
    }

    public void destroy() {
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

	new MainFrame(new visualtra(), 700, 700);
    }
}
