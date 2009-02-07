package org.jboss.webbeans.porting;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.el.ELContext;
import javax.inject.manager.Manager;

import org.jboss.webbeans.ManagerImpl;
import org.jboss.webbeans.mock.MockBootstrap;
import org.jboss.webbeans.mock.MockWebBeanDiscovery;
import org.jboss.webbeans.mock.el.EL;
import org.jboss.webbeans.tck.spi.StandaloneContainers;

public class ContainersImpl implements StandaloneContainers
{
   
   public Manager deploy(List<Class<? extends Annotation>> enabledDeploymentTypes, Class<?>... classes)
   {
      MockBootstrap bootstrap = new MockBootstrap();
      ManagerImpl manager = bootstrap.getManager();
      if (enabledDeploymentTypes != null)
      {
         manager.setEnabledDeploymentTypes(enabledDeploymentTypes);
      }
      bootstrap.setWebBeanDiscovery(new MockWebBeanDiscovery(classes));
      bootstrap.boot();
      return manager;
   }
   
   public Manager deploy(java.lang.Class<?>... classes) 
   {
      return deploy(null, classes);
   }
   
   public Manager deploy(InputStream archive)
   {
      throw new UnsupportedOperationException();
   }
   
   public Manager deploy(List<Class<? extends Annotation>> enabledDeploymentTypes, InputStream archive)
   {
      throw new UnsupportedOperationException();
   }
   
   @SuppressWarnings("unchecked")
   public <T> T evaluateValueExpression(String expression, Class<T> expectedType)
   {
      ELContext elContext = EL.createELContext();
      return (T) EL.EXPRESSION_FACTORY.createValueExpression(elContext, expression, expectedType).getValue(elContext);
   }
 
   @SuppressWarnings("unchecked")
   public <T> T evaluateMethodExpression(String expression, Class<T> expectedType, Class<?>[] expectedParamTypes, Object[] expectedParams)
   {
      ELContext elContext = EL.createELContext();
      return (T) EL.EXPRESSION_FACTORY.createMethodExpression(elContext, expression, expectedType, expectedParamTypes).invoke(elContext, expectedParams);
   }
}