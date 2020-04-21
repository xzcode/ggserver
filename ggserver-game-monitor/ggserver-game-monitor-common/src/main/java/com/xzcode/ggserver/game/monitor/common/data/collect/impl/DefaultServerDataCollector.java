package com.xzcode.ggserver.game.monitor.common.data.collect.impl;

import java.util.ArrayList;
import java.util.List;

import com.xzcode.ggserver.game.monitor.common.data.collect.DataCollector;
import com.xzcode.ggserver.game.monitor.common.data.model.server.FileStoreInfo;
import com.xzcode.ggserver.game.monitor.common.data.model.server.ServerData;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import xzcode.ggserver.core.common.executor.ITaskExecutor;

/**
 * 默认服务器系统信息收集器
 *
 * @author zai
 * 2020-04-21 16:00:49
 */
public class DefaultServerDataCollector implements DataCollector<ServerData>{

	protected SystemInfo si = new SystemInfo();
	
	//操作系统
	protected OperatingSystem os = si.getOperatingSystem();
	
	//硬件抽象层
	protected HardwareAbstractionLayer hal = si.getHardware();;
	
	//处理器
	protected CentralProcessor processor = hal.getProcessor();
	
	//全局内存
	protected GlobalMemory memory = hal.getMemory();

	
	//文件系统
	protected FileSystem fileSystem;
	
	//网络接口
	protected NetworkIF[] networkIFs = hal.getNetworkIFs();
	
	protected ITaskExecutor taskExecutor;
	
	protected String cpuInfo;
	
	protected long[] prevTicks;
	
	@Override
	public ServerData collect() {
		
		
		ServerData serverData = new ServerData();
		serverData.setOs(os.toString());
		serverData.setCpu(getCpuInfo());
		serverData.setTotalMemory(memory.getTotal());
		serverData.setUsedMemory(memory.getTotal() - memory.getAvailable());
		serverData.setTotalVirtualMemory(memory.getVirtualMemory().getSwapTotal());
		serverData.setUsedVirtualMemory(memory.getVirtualMemory().getSwapUsed());
		serverData.setThreads(os.getThreadCount());
		serverData.setProcesses(os.getProcessCount());
		
		long[] ticks = processor.getSystemCpuLoadTicks();
		long totalCpu = getTotalCpu(ticks, ticks);
		serverData.setCpuUse(getCpuUse(ticks, ticks, totalCpu));
		
		
		
		
		this.prevTicks = ticks;
		return serverData;
	}
	
	public String getCpuInfo() {
		if (this.cpuInfo == null) {
			StringBuilder sb = new StringBuilder(128);
			sb
			.append(processor.toString())
			.append(" ")
			.append(processor.getPhysicalPackageCount())
			.append(" CPU ")
			.append(processor.getPhysicalProcessorCount())
			.append(" Cores ")
			.append(processor.getLogicalProcessorCount())
			.append(" Threads");
			this.cpuInfo = sb.toString();
		}
		return cpuInfo;
	}
	
	public double getCpuUse(long[] ticks, long[] prevTicks, long totalCpu) {
		long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
		return 100d * idle / totalCpu;
	}
	
	public long getTotalCpu(long[] ticks, long[] prevTicks) {
		long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
		long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
		long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
		long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
		long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
		long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
		long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
		long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
		long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
		return totalCpu;
	}
	
	/**
	 * 获取文件系统存储信息
	 *
	 * @return
	 * @author zai
	 * 2020-04-21 18:37:59
	 */
	public List<FileStoreInfo> getFileStoreInfos() {
		List<FileStoreInfo> fileStoreInfos = new ArrayList<>();
		OSFileStore[] fsArray = fileSystem.getFileStores();
		for (OSFileStore fs : fsArray) {
			FileStoreInfo info = new FileStoreInfo();
			info.setName(fs.getLogicalVolume() == null ? fs.getName() : fs.getLogicalVolume());
			info.setTotalSpace(fs.getTotalSpace());
			info.setUsableSpace(fs.getUsableSpace());
			fileStoreInfos.add(info);
		}
		
		return fileStoreInfos;
	}
	
	public void startUpdateTask() {
		
	}

}
