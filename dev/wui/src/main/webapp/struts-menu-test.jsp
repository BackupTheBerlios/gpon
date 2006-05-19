<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Tabbed Menu (with Velocity) Example</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <link rel="stylesheet" type="text/css" media="screen" 
        href="css/tabs.css" />    

    <script type="text/javascript" src="scripts/tabs.js"></script>
</head>
<body>

<div id="menuDiv">
<menu:useMenuDisplayer name="Velocity" config="/templates/tabs.html"
    bundle="org.apache.struts.action.MESSAGE">
    <ul id="menuList">
    <menu:displayMenu name="TabbedHome"/>
    <menu:displayMenu name="TabbedAbout"/>
    <menu:displayMenu name="TabbedContact"/>
    <menu:displayMenu name="TabbedExit"/>
    </ul>
</menu:useMenuDisplayer>
</div>

<div id="content" style="width: 60%">
    <h2>CSS Tabs with Submenus</h2>
    <p>
        This example uses a Velocity template to render its HTML. Otherwise,
        it's the same as the <a href="tabbedMenu.jsp?Home">Tabbed Menu Example</a>.
        However, this one is much better (in my opinion) because you have full
        control over the HTML (via Velocity templates) and display logic. 
    </p>
</div>
</body>
</html>
