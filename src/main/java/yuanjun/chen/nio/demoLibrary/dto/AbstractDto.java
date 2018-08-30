/**  
 * @Title: AbstractDto.java   
 * @Package: yuanjun.chen.nio.demoLibrary.dto   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年8月30日 下午7:13:09   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.nio.demoLibrary.dto;

/**   
 * @ClassName: AbstractDto   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年8月30日 下午7:13:09  
 */
abstract public class AbstractDto {
    private short module;

    private short command;

    private byte[] bizData;

    /**
     * @return the module
     */
    public short getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(short module) {
        this.module = module;
    }

    /**
     * @return the command
     */
    public short getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(short command) {
        this.command = command;
    }

    /**
     * @return the bizData
     */
    public byte[] getBizData() {
        return bizData;
    }

    /**
     * @param bizData the bizData to set
     */
    public void setBizData(byte[] bizData) {
        this.bizData = bizData;
    }
}
