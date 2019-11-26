package org.mybatis.generator.hh;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ParseJava {
	
	public static void main(String[] args) throws Exception {
		InputStream in = null;
		try {
			Path p = Paths.get("D:\\genSrc\\com\\minsi\\api\\dao\\TRoleResourceDao.java");
//			Path p = Paths.get("D:\\work\\github\\springboot-vue\\back-end\\common\\src\\main\\java\\org\\niuhuang\\io\\ZipUtil.java");
			in = Files.newInputStream(p);
			CompilationUnit cu = StaticJavaParser.parse(in);
//			NodeList<ImportDeclaration> imports = cu.getImports();
//			for(ImportDeclaration item : imports) {
//				System.out.println(item.getName());
//				System.out.println(item.toString());
//			}
//			
//			 Optional<PackageDeclaration> _package = cu.getPackageDeclaration();
//			 System.out.println(_package.get().getName());
//		
//			 NodeList<TypeDeclaration<?>> _types = cu.getTypes();
//			 for(TypeDeclaration item : _types) {
////					System.out.println(item.getName());
////				 	System.out.println(item.toString());
//				 	NodeList<BodyDeclaration<?>> _members = item.getMembers();
//				 	for(BodyDeclaration m : _members) {
////				 		System.out.println(m.toString());
//				 		Optional<MethodDeclaration> b = m.toMethodDeclaration();
//				 		try {
//				 				System.out.println(b.get().getDeclarationAsString(true,true,true));
//					 	} catch(NoSuchElementException e) {
//				 			
//				 		}
//				 		
//				 		
//				 	}
//				}
			
			new MethodVisitor().visit(cu, null);
			 
			
			/*
			 * cu.findAll(ClassOrInterfaceDeclaration.class).stream().forEach(c -> { String
			 * oldName = c.getNameAsString(); System.out.println(oldName); });
			 * System.out.println(cu.toString());
			 */
		} finally {
			in.close();
		}
		
	}
	
	 private static class MethodVisitor extends VoidVisitorAdapter {

	        @Override
	        public void visit(MethodDeclaration n, Object arg) {
	            // here you can access the attributes of the method.
	            // this method will be called for all methods in this 
	            // CompilationUnit, including inner class methods
	            System.out.println(n.getDeclarationAsString(true,true,true));
	            n.accept(new ParameterVisitor(), null);
	            
	        }
	    }
	 
	 private static class ParameterVisitor extends VoidVisitorAdapter {
		 
		 @Override
	        public void visit(Parameter n, Object arg) {
	            // here you can access the attributes of the method.
	            // this method will be called for all methods in this 
	            // CompilationUnit, including inner class methods
	            System.out.println(n.getType() + ","+ n.getName());
	        }
		 
	 }
	
	 
	

}
