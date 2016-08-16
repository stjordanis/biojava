package org.biojava.nbio.structure.io.mmtf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import org.biojava.nbio.structure.Structure;
import org.rcsb.mmtf.decoder.GenericDecoder;
import org.rcsb.mmtf.decoder.StructureDataToAdapter;
import org.rcsb.mmtf.decoder.ReaderUtils;
import org.rcsb.mmtf.encoder.AdapterToStructureData;
import org.rcsb.mmtf.encoder.WriterUtils;

/**
 * A class of functions for reading and writing Biojava structures using MMTF
 * @author Anthony Bradley
 *
 */
public class MmtfActions {
	
	/**
	 * Get a Structure object from a mmtf file.
	 * @param filePath the mmtf file
	 * @return a Structure object relating to the input byte array.
	 * @throws IOException 
	 */
	public static Structure readFromFile(Path filePath) throws IOException {
		// Get the reader - this is the bit that people need to implement.
		MmtfStructureReader mmtfStructureReader = new MmtfStructureReader();
		// Do the inflation
		new StructureDataToAdapter(new GenericDecoder(ReaderUtils.getDataFromFile(filePath)), mmtfStructureReader);
		// Get the structue
		return mmtfStructureReader.getStructure();
	}
	
	/**
	 * Write a Structure object to a file.
	 * @param structure the Structure to write
	 * @param path the file to write
	 * @throws IOException
	 */
	public static void writeToFile(Structure structure, Path path) throws IOException {
		// Set up this writer
		AdapterToStructureData writerToEncoder = new AdapterToStructureData();
		// Get the writer - this is what people implement
		new MmtfStructureWriter(structure, writerToEncoder);
		// Now write this data to file
		WriterUtils.writeDataToFile(writerToEncoder, path);
	}
	
	/**
	 * Write a Structure object to an {@link OutputStream}
	 * @param structure the Structure to write
	 * @return the {@link OutputStream} of the MMTF structure
	 * @throws IOException an error transferring the byte[]
	 */
	public static OutputStream writeToOutputStream(Structure structure) throws IOException{
		// Set up this writer
		AdapterToStructureData writerToEncoder = new AdapterToStructureData();
		// Get the writer - this is what people implement
		new MmtfStructureWriter(structure, writerToEncoder);
		// Now write this data to file
		byte[] outputBytes = WriterUtils.getDataAsByteArr(writerToEncoder);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(outputBytes.length);
		byteArrayOutputStream.write(outputBytes,0,outputBytes.length);
		return byteArrayOutputStream;
	}

	
	/**
	 * Get a Biojava structure from the mmtf REST service.
	 * @param pdbId the PDB code of the required structure
	 * @return a Structure object relating to the input byte array
	 * @throws IOException 
	 */
	public static Structure readFromWeb(String pdbId) throws IOException {
		// Get the reader - this is the bit that people need to implement.
		MmtfStructureReader mmtfStructureReader = new MmtfStructureReader();
		// Do the inflation
		new StructureDataToAdapter(new GenericDecoder(ReaderUtils.getDataFromUrl(pdbId)), mmtfStructureReader);
		// Get the structue
		return mmtfStructureReader.getStructure();
	}
}