package fr.uvsq.pglp.project;

import java.util.ArrayList;

// Entry interface representing both files and directories
interface Entry extends Iterable<Entry> {

  String getName();

  String getPath();

  int getSize();

  int getNer();

  void setNer(int ner);

  String getBasePath();

  ArrayList<String> getAnnotations();

  void addAnnotation(String annotation);
}
