// MIT license
// Author: Egon Willighagen

@Grab(group='org.apache.any23', module='apache-any23-core', version='2.7')
@Grab(group='io.github.egonw.bacting', module='managers-rdf', version='0.4.0')

import org.apache.any23.Any23
import org.apache.any23.source.HTTPDocumentSource
import org.apache.any23.writer.NTriplesWriter

workspaceRoot = "../ws"
rdf = new net.bioclipse.managers.RDFManager(workspaceRoot);

ontology = args[0]
file = "docs/${ontology}.html"

Any23 runner = new Any23();

out = new ByteArrayOutputStream();
handler = new NTriplesWriter(out);
try { runner.extract(new File(file), handler);
} finally { handler.close(); }

n3Stream = new ByteArrayInputStream(out.toByteArray())

kb = rdf.createInMemoryStore()
rdf.addPrefix(kb, "gpml",   "http://vocabularies.wikipathways.org/gpml#")
rdf.addPrefix(kb, "wp",     "http://vocabularies.wikipathways.org/wp#")
rdf.addPrefix(kb, "wpTypes","http://vocabularies.wikipathways.org/wpTypes#")
rdf.addPrefix(kb, "biopax", "http://www.biopax.org/release/biopax-level3.owl#")
rdf.addPrefix(kb, "gpml",   "http://vocabularies.wikipathways.org/gpml#")
rdf.addPrefix(kb, "obo",    "http://purl.obolibrary.org/obo/")
rdf.addPrefix(kb, "owl",    "http://www.w3.org/2002/07/owl#")
rdf.addPrefix(kb, "rdf",    "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
rdf.addPrefix(kb, "rdfs",   "http://www.w3.org/2000/01/rdf-schema#")
rdf.addPrefix(kb, "skos",   "http://www.w3.org/2004/02/skos/core#")
rdf.addPrefix(kb, "void",   "http://rdfs.org/ns/void#")
rdf.addPrefix(kb, "wp",     "http://vocabularies.wikipathways.org/wp#")
rdf.addPrefix(kb, "xsd",    "http://www.w3.org/2001/XMLSchema#")

rdf.importFromStream(kb, n3Stream, "N3")

println rdf.asRDFN3(kb)
