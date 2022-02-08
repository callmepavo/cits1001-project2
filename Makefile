# Builds Aquarium

JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Aquarium.java AquariumViewer.java CheckSolution.java FileIO.java Run.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class