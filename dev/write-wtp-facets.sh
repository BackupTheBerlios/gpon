cat <<EOF > ./wui/.settings/org.eclipse.wst.common.project.facet.core.xml
<faceted-project>
  <fixed facet="jst.java"/>
  <fixed facet="jst.web"/>
  <installed facet="jst.web" version="2.3"/>
  <installed facet="jst.java" version="1.4"/>
</faceted-project>
EOF

cp ./wui/.settings/org.eclipse.wst.common.project.facet.core.xml ./ws/.settings/org.eclipse.wst.common.project.facet.core.xml
