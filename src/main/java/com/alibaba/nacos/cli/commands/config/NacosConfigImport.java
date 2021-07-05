package com.alibaba.nacos.cli.commands.config;

import com.alibaba.nacos.cli.core.LogicHandler;
import com.alibaba.nacos.cli.core.exception.HandlerException;
import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.alibaba.nacos.cli.utils.HintUtils.*;

/**
 * create a config by uploading a local file
 *
 * @author lehr
 */
@Deprecated
@CommandLine.Command(
    name = NAME_IMPORT,
    sortOptions = SORT_OPTIONS,
    headerHeading = HEADER_HEADING,
    synopsisHeading = SYNOPSIS_HEADING,
    descriptionHeading = DESCRIPTION_HEADING,
    parameterListHeading = PARAMETER_LIST_HEADING,
    optionListHeading = OPTION_LIST_HEADING,
    header = "import configurations",
    description = "Create a config by uploading a local file.")
public class NacosConfigImport implements Runnable {

  @CommandLine.Parameters(paramLabel = "<group>", description = "Configuration group.")
  String group;

  @CommandLine.Parameters(paramLabel = "<dataId>", description = "Configuration ID.")
  String dataId;

  @CommandLine.Parameters(
      paramLabel = "<file>",
      description = "The config file path you wanna import.")
  File file;

  @CommandLine.Option(
      names = {"-t", "--type"},
      paramLabel = "<type>",
      description = "Configuration type.")
  String type;

  @Override
  public void run() {

    try (FileInputStream fis = new FileInputStream(file)) {
      int length = fis.available();
      byte[] bytes = new byte[length];
      fis.read(bytes);
      String content = new String(bytes);
      LogicHandler.postConfig(group, dataId, content, type);
    } catch (FileNotFoundException e) {
      System.out.println("file not exists!");
    } catch (IOException e) {
      System.out.println("failed to read file");
    } catch (HandlerException e) {
      System.out.println(e.getMessage());
    }
  }
}